package org.nanocan.rppa.scanner

import groovy.sql.Sql
import org.nanocan.rppa.layout.LayoutSpot
import org.nanocan.rppa.layout.NoMatchingLayoutException

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
        try{
            performSqlBatchInsert(sql, spots, slideInstance)
        }catch(NoMatchingLayoutException nmle)
        {
            return "No matching layout found for spot ${nmle.obj}"
        }

        //clean up
        sql.close()

        //refresh slide because hibernate does not know about our changes
        slideInstance.refresh()

        return slideInstance
    }

    //use hibernate batch with prepared statements for max performance
    def performSqlBatchInsert(sql, spots, slideInstance){
        //config
        def groovySql = grailsApplication.config.rppa.jdbc.groovySql.toString().toBoolean()
        def batchSize = grailsApplication.config.rppa.jdbc.batchSize?:150

        //extract deposition pattern from layout
        def depositionList = depositionService.parseDepositions(slideInstance.layout.depositionPattern)
        int deposLength = depositionList.size()

        //sort rows in obj like this block -> column -> row
        spots.sort{ a,b -> (a.block <=> b.block) ?: (a.row <=> b.row) ?:(a.col <=> b.col)  }

        //get all layout spots in the correct order
        def layoutSpots = fetchOrderedLayoutSpots(slideInstance.layout.id)

        def layoutSpotIterator = layoutSpots.iterator()
        def currentLayoutSpot

        if(layoutSpotIterator.hasNext())
            currentLayoutSpot = layoutSpotIterator.next()

        else throw new NoMatchingLayoutException(obj: null)


        if(!groovySql){
            spots.eachWithIndex{ obj, currentSpotIndex ->

                int layoutColumn = mapToLayoutColumn(obj.col, deposLength)
                currentLayoutSpot = scrollThroughLayoutSpots(currentLayoutSpot, layoutSpotIterator, obj, layoutColumn)

                //add new spot
                def newSpot = new Spot(obj)
                newSpot.layoutSpot = currentLayoutSpot
                newSpot.slide = slideInstance
                newSpot.save()

                nextStep = updateProgressBar(nextStep, currentSpotIndex)
            }
        }

        else{
            sql.withBatch(batchSize, 'insert into spot (version, bg, fg, block, col, diameter, flag, layout_spot_id, row, slide_id, x, y, signal) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)'){ stmt ->

                spots.eachWithIndex{ obj, currentSpotIndex ->
                    int layoutColumn = mapToLayoutColumn(obj.col, deposLength)

                    //match a layout spot to this slide spot in a closure
                    currentLayoutSpot = scrollThroughLayoutSpots(currentLayoutSpot, layoutSpotIterator, obj, layoutColumn)

                    //add insert statement to batch
                    stmt.addBatch(0, obj.BG, obj.FG, obj.block, obj.col, obj.diameter, obj.flag, currentLayoutSpot.id, obj.row, slideInstance.id, obj.x, obj.y, obj.FG-obj.BG)

                    nextStep = updateProgressBar(nextStep, currentSpotIndex)
                }
            }
        }
    }

    public fetchOrderedLayoutSpots(layoutId) {
        def layoutSpots = LayoutSpot.withCriteria {
            eq("layout.id", layoutId)
            order('block')
            order('row')
            order('col')
        }
        return layoutSpots
    }

    public scrollThroughLayoutSpots(def currentLayoutSpot, def layoutSpotIterator, def obj, int layoutColumn) {

        //scroll forward
        while ((currentLayoutSpot.row < (obj.row as int) || currentLayoutSpot.block < (obj.block as int)
                || currentLayoutSpot.col < layoutColumn) && layoutSpotIterator.hasNext()) {
            currentLayoutSpot = layoutSpotIterator.next()
        }

        //check if we missed the right spot
        if(currentLayoutSpot.row == obj.row && currentLayoutSpot.block == obj.block && currentLayoutSpot.col == layoutColumn)
        {
            return currentLayoutSpot
        }

        else
        {
            throw new NoMatchingLayoutException(obj)
        }
    }

    def mapToLayoutColumn(col, deposLength) {
        //match layout column to actual column
        int layoutColumn = (((col as Integer) - 1) / deposLength)
        //we don't want to start with zero
        layoutColumn++
        return layoutColumn
    }

}
