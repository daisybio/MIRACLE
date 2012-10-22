package org.nanocan.rppa.spotting

import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.savanah.plates.WellLayout
import org.nanocan.rppa.layout.LayoutSpot

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 18-10-12
 * Time: 16:45
 */
abstract class Spotter {

    def slideLayout = new SlideLayout()
    def maxSpottingRows
    def maxSpottingColumns
    def maxExtractorColumns
    def maxExtractorRows
    def grailsApplication
    def currentSpottingColumn = 1
    def currentSpottingRow = 1

    Spotter(Map map)
    {
        this.grailsApplication = map.grailsApplication
        this.maxSpottingRows = map.maxSpottingRows?:18
        this.maxSpottingColumns = map.maxSpottingColumns?:2
        this.maxExtractorColumns = map.maxExtractorColumns?:12
        this.maxExtractorRows = map.maxExtractorRows?:4
    }

    def spot384(List<WellLayout> extraction) {

        def extractionSorted = sortExtraction(extraction)

        extractionSorted.each{
            createLayoutSpot(it, null, it.row, it.col)
        }

        nextSpot()
    }

    def spot96as384(List<WellLayout> extraction) {

        def parseRow = {row -> 1 + (row - 1) * 2}
        def parseColumn = {col -> 1 + (col - 1) * 2}

        def extractionSorted = sortExtraction(extraction)

        extractionSorted.each {

            def row = parseRow(it.row)
            def col = parseColumn(it.col)

            for(int subCol in 0..1)
            {
                for(int subRow in 0..1)
                {
                    println "row:" +(row + subRow) + "|col:"+ (col + subCol) + "|block:" + calculateBlockFromRowAndCol(row + subRow, col + subCol) + "|spotRow:" + currentSpottingRow + "|spotCol" + currentSpottingColumn
                    createLayoutSpot(it, null, (row + subRow), (col + subCol))
                }
            }
        }

        nextSpot()
    }

    abstract void nextSpot()

    abstract List<WellLayout> sortExtraction(List<WellLayout> extraction)

    boolean isFull(){
        if (currentSpottingColumn > maxSpottingColumns || currentSpottingRow > maxSpottingRows) return true
        else return false
    }

    def calculateBlockFromRowAndCol(int row, int col)
    {
        //get extractor position from plate position
        def modRow = (row % maxExtractorRows)
        if(modRow == 0 ) modRow = maxExtractorRows

        def modCol = (col % maxExtractorColumns)
        if (modCol == 0) modCol = maxExtractorColumns

        //convert into block number
        (((modRow - 1) * maxExtractorColumns) + modCol)
    }

    def createLayoutSpot(wellLayout, dilutionFactor, row, column){

        if (isFull()) throw new Exception("Layout is full. Can not add ${wellLayout}.")

        def propInstance
        def props = [:]

        for(prop in ["cellLine", "inducer", "treatment"])
        {
            if (wellLayout."${prop}"){
                def domainClass = grailsApplication.getClassForName(prop.toString().capitalize())

                //check if property exists in MIRACLE
                propInstance = domainClass.findByName(wellLayout."${prop}".name)

                //if property is given in layout, but does not yet exist in MIRACLE, create it.
                if(!propInstance)
                {
                    propInstance = domainClass.newInstance()
                    propInstance.name = wellLayout."${prop}".name
                    propInstance.color = wellLayout."${prop}".color
                    propInstance.save(flush: true)
                }
            }

            props.put(prop, propInstance)
        }

        //else property is null and remains null

        def newLayoutSpot = new LayoutSpot(block: calculateBlockFromRowAndCol(row, column),
                cellLine: props.cellLine, dilutionFactor: dilutionFactor, col: currentSpottingColumn, row: currentSpottingRow, inducer: props.inducer,
                treatment: props.treatment, layout: slideLayout)

        slideLayout.addToSampleSpots(newLayoutSpot)
    }
}
