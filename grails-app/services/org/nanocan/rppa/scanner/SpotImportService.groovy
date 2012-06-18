package org.nanocan.rppa.scanner

import groovy.sql.Sql
import org.nanocan.rppa.layout.LayoutSpot

/**
 * This service handles the extraction from spot information from an excel sheet using the excel-import plugin.
 * The spots are persisted in the database using groovy sql (for performance reasons)
 */
class SpotImportService {

    //dependencies
    def progressService
    def depositionService
    def dataSourceUnproxied
    def grailsApplication

    /**
     * Get name of sheets of an excel file
     * @param slideInstance
     * @return
     */
    def getSheets(def slideInstance)
    {
        def resultFile = slideInstance.resultFile

        ResultFileImporter importer = new ResultFileImporter()

        def fis = new FileInputStream(resultFile.filePath)

        importer.readFromStream(fis)
        def sheets = importer.getSheets()

        fis.close();

        return sheets
    }

    /**
     * Efficiently delete all spots that belong to a slide
     */
    def deleteSpots = { slideInstanceId ->

        //config
        def groovySql = grailsApplication.config.rppa.jdbc.groovySql.toString().toBoolean()

        if(groovySql)
        {
            def sql = Sql.newInstance(dataSourceUnproxied)
            sql.execute('delete from spot where slide_id = ?', slideInstanceId)
            sql.close()
            Slide.get(slideInstanceId).refresh()
        }

        else Slide.get(slideInstanceId).spots.clear()
    }

    /**
     * Import excel file using a fileinputstream
     * ResultFileConfig is a map of column letters to domain class properties, e.g. Signal is in column F, ...
     */
    def importSpotsFromExcel(String filePath, String sheetName, ResultFileConfig rfc) {

        ResultFileImporter importer = new ResultFileImporter()
        def fis = new FileInputStream(filePath)
        importer.readFromStream(fis)

        def spots = importer.getSpots(sheetName, rfc)
        fis.close()

        return(spots)
    }

    //keep track of the progress, but update only every 1% to reduce the overhead
    def initializeProgressBar = { spots ->
        progressService.setProgressBarValue("excelimport", 0)
        def numberOfSpots = spots.size()

        onePercent = (int) (numberOfSpots / 100)
        return onePercent
    }

    def updateProgressBar = { nextStep, currentSpotIndex ->
        if(currentSpotIndex == nextStep)
        {
            nextStep += onePercent
            progressService.setProgressBarValue("excelimport", currentPercent++)
        }

        return nextStep
    }

    /*
     * Some global variables
     */
    //we want to keep track of the last layout spot to reduce the database load
    int lastBlock = -1
    int lastRow = -1
    int lastCol = -1
    def lastLayoutSpot

    //global variables for progress bar
    def currentPercent = 0
    def onePercent
    def nextStep

    /**
     * Main method
     */
    def processResultFile(def slideInstance, String sheetName, ResultFileConfig rfc)
    {
        def spots = importSpotsFromExcel(slideInstance.resultFile.filePath, sheetName, rfc)

        nextStep = initializeProgressBar(spots)

        //create an sql instance for direct inserts via groovy sql
        def sql = Sql.newInstance(dataSourceUnproxied)

        //insert spots
        performSqlBatchInsert(sql, spots, slideInstance)

        //clean up
        sql.close()

        //refresh slide because hibernate does not know about our changes
        slideInstance.refresh()

        return null
    }

    //use hibernate batch with prepared statements for max performance
    def performSqlBatchInsert = {sql, spots, slideInstance ->

        //config
        def groovySql = grailsApplication.config.rppa.jdbc.groovySql.toString().toBoolean()
        def batchSize = grailsApplication.config.rppa.jdbc.batchSize?:150

        //extract deposition pattern from layout
        def depositionList = depositionService.parseDepositions(slideInstance.layout.depositionPattern)
        int deposLength = depositionList.size()
        int layId = slideInstance.layout.id

        if(!groovySql){

            spots.eachWithIndex{ obj, currentSpotIndex ->

                //match a layout spot to this slide spot in a closure
                def layoutSpot = matchLayoutSpot(sql, obj, layId, deposLength)

                //add new spot
                def newSpot = new Spot(obj)
                newSpot.layoutSpot = layoutSpot
                newSpot.slide = slideInstance
                newSpot.save()

                nextStep = updateProgressBar(nextStep, currentSpotIndex)
            }
        }

        else{
            sql.withBatch(batchSize, 'insert into spot (version, bg, fg, block, col, diameter, flag, layout_spot_id, row, slide_id, x, y, signal) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)'){ stmt ->

                spots.eachWithIndex{ obj, currentSpotIndex ->

                    //match a layout spot to this slide spot in a closure
                    def layoutSpot = matchLayoutSpot(sql, obj, layId, deposLength)

                    //add insert statement to batch
                    stmt.addBatch(0, obj.BG, obj.FG, obj.block, obj.col, obj.diameter, obj.flag, layoutSpot.id, obj.row, slideInstance.id, obj.x, obj.y, obj.FG-obj.BG)

                    nextStep = updateProgressBar(nextStep, currentSpotIndex)
                }
            }
        }
    }

    /**
     * Closure to find a layout spot that matches the current spot
     */
    def matchLayoutSpot = {sql, obj, layId, deposLength ->

        //config
        def groovySql = grailsApplication.config.rppa.jdbc.groovySql.toString().toBoolean()

        def layoutSpot

        //match layout column to actual column
        int layoutColumn = (((obj.col as Integer)-1) / deposLength)
        //we don't want to start with zero
        layoutColumn++

        //if within one deposition pattern the layout shouldn't change
        if(lastBlock == obj.block && lastRow == obj.row && lastCol == layoutColumn){
            layoutSpot = lastLayoutSpot
        }

        else
        {
            lastBlock = obj.block
            lastRow = obj.row
            lastCol = layoutColumn

            if(!groovySql){
                layoutSpot = LayoutSpot.find {
                    ( layout.id == (layId as Long)
                            && block == (obj.block as Integer)
                            && row == (obj.row as Integer)
                            && col == layoutColumn )
                }
            }

            else {
                layoutSpot = sql.firstRow("select * from layout_spot where layout_id = ? and block = ? and row = ? and col = ?",
                        [layId, obj.block, obj.row, layoutColumn])
            }

            //check if a layout spot was found, if not abort
            if(layoutSpot == null){
                progressService.setProgressBarValue("excelimport", 100)
                return "Error: No layout information found"
            }

            //save spot for next iteration
            else{
                lastLayoutSpot = layoutSpot
            }
        }

        return layoutSpot
    }

}
