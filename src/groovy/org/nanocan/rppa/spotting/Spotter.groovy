package org.nanocan.rppa.spotting

import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.savanah.plates.WellLayout
import org.nanocan.rppa.layout.LayoutSpot
import org.nanocan.rppa.layout.Dilution
import org.nanocan.rppa.rnai.Sample
import org.nanocan.rppa.layout.SpotType

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

    Spotter(Map map)
    {
        this.grailsApplication = map.grailsApplication
        this.maxSpottingRows = map.maxSpottingRows?:18
        this.maxSpottingColumns = map.maxSpottingColumns?:2
        this.maxExtractorColumns = map.maxExtractorColumns?:12
        this.maxExtractorRows = map.maxExtractorRows?:4
        this.matchingMaps = map.matchingMaps
    }

    def spot384(List<WellLayout> extraction) {

        def extractionSorted = sortExtraction(extraction)

        extractionSorted.each{
            createLayoutSpot(it, null, it.row, it.col)
        }

        nextSpot()
    }

    def spot96as384(List<WellLayout> extraction){
        spot96as384(extraction, null)
    }

    def spot96as384(List<WellLayout> extraction, dilutionPattern) {

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
                    createLayoutSpot(it, dilutionPattern[subRow][subCol], (row + subRow), (col + subCol))
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

        def props = [:]

        for(prop in ["cellLine", "inducer", "treatment", "numberOfCellsSeeded", "sample"])
        {
            def propInstance

            if (wellLayout."${prop}"){

                def domainClass = grailsApplication.getClassForName("org.nanocan.rppa.layout." + prop.toString().capitalize())

                //if this is a savanah well layout, we need to get the corresponding miracle properties from a match list
                if (wellLayout instanceof org.nanocan.savanah.plates.WellLayout)
                {
                    propInstance = domainClass.findByName(matchingMaps[prop].get(wellLayout."${prop}".name))
                }

                else propInstance = domainClass.findByName(wellLayout."${prop}".name)
            }

            props.put(prop, propInstance)
        }

        //spot type
        if (wellLayout instanceof org.nanocan.savanah.plates.WellLayout)
        {
            props.put("spotType", null)
        }
        else
        {
            props.put("spotType", wellLayout.spotType)
        }

        if (dilutionFactor) dilutionFactor = Dilution.findByDilutionFactor(dilutionFactor as Double)

        def newLayoutSpot = new LayoutSpot(block: calculateBlockFromRowAndCol(row, column),
                cellLine: props.cellLine, dilutionFactor: dilutionFactor, col: currentSpottingColumn, row: currentSpottingRow, inducer: props.inducer,
                treatment: props.treatment, numberOfCellsSeeded: props.numberOfCellsSeeded, spotType: props.spotType, sample: props.sample, layout: slideLayout)

        slideLayout.addToSampleSpots(newLayoutSpot)
    }
}
