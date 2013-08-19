package org.nanocan.rppa.spotting

import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.rppa.layout.WellLayout
import org.nanocan.rppa.layout.LayoutSpot
import org.nanocan.rppa.layout.Dilution

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
    def matchingMaps
    def defaultSpotType
    def defaultLysisBuffer
    def replicateCounter = 0

    Spotter(Map map)
    {
        this.grailsApplication = map.grailsApplication
        this.maxSpottingRows = map.maxSpottingRows?:18
        this.maxSpottingColumns = map.maxSpottingColumns?:2
        this.maxExtractorColumns = map.maxExtractorColumns?:12
        this.maxExtractorRows = map.maxExtractorRows?:4
        this.matchingMaps = map.matchingMaps
        this.defaultSpotType = map.defaultSpotType
        this.defaultLysisBuffer = map.defaultLysisBuffer
    }

    def spot384(def extraction) {

        def extractionSorted = sortExtraction(extraction)

        extractionSorted.each{
            createLayoutSpot(it, replicateCounter.toString(), null, it.row, it.col)
            replicateCounter++
        }

        nextSpot()
    }

    def spot96as384(def extraction){
        spot96as384(extraction, null)
    }

    def spot96as384(def extraction, dilutionPattern) {

        def parseRow = {row -> 1 + (row - 1) * 2}
        def parseColumn = {col -> 1 + (col - 1) * 2}

        def extractionSorted = sortExtraction(extraction)

        extractionSorted.each {

            def row = parseRow(it.row)
            def col = parseColumn(it.col)
            int replicate = this.replicateCounter++

            for(int subCol in 0..1)
            {
                for(int subRow in 0..1)
                {
                    createLayoutSpot(it, replicate.toString(), dilutionPattern[subRow][subCol], (row + subRow), (col + subCol))
                }
            }
        }

        nextSpot()
    }

    abstract void nextSpot()

    abstract def sortExtraction(def extraction)

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

    def createLayoutSpot(wellLayout, replicate, dilutionFactor, row, column){

        if (isFull()) throw new Exception("Layout is full. Can not add ${wellLayout}.")

        def props = [:]

        for(prop in ["cellLine", "inducer", "treatment", "numberOfCellsSeeded", "sample"])
        {
            def propInstance
            wellLayout.attach()

            if (wellLayout."${prop}")  propInstance = wellLayout."${prop}"

            props.put(prop, propInstance)
        }

        def wellLayoutMiracle = null

        //spot type
        if(wellLayout instanceof WellLayout)
        {
            wellLayoutMiracle = wellLayout
            props.put("spotType", wellLayout.spotType)
        }

        if (dilutionFactor) dilutionFactor = Dilution.get(dilutionFactor)

        def newLayoutSpot = new LayoutSpot(wellLayout: wellLayoutMiracle, replicate: replicate, block: calculateBlockFromRowAndCol(row, column),
                cellLine: props.cellLine, dilutionFactor: dilutionFactor, col: currentSpottingColumn, row: currentSpottingRow, inducer: props.inducer,
                lysisBuffer: defaultLysisBuffer, treatment: props.treatment, numberOfCellsSeeded: props.numberOfCellsSeeded, spotType: props.spotType?:defaultSpotType, sample: props.sample, layout: slideLayout)

        slideLayout.addToSampleSpots(newLayoutSpot)
    }
}
