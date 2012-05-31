package org.nanocan.rppa.layout

import org.apache.commons.lang.ArrayUtils
import org.nanocan.rppa.scanner.Spot

class SlideLayoutService {

    def createSampleSpots(SlideLayout slideLayout) {

        //def depositions = getDepositionIntArray(slideLayout.depositionPattern)

        for (int block = 1; block <= slideLayout.numberOfBlocks; block++) {
            for (int col = 1; col <= slideLayout.columnsPerBlock; col++) {
                for (int row = 1; row <= slideLayout.rowsPerBlock; row++) {
                    slideLayout.addToSampleSpots(new LayoutSpot(block: block, col: col, row: row))
                }
            }
        }

        slideLayout.save(flush: true, failOnError: true)
    }

    def parseDepositions(String depositionPattern)
    {
        //remove brackets and split by ,
        def cut = depositionPattern.substring(1, depositionPattern.length()-1)
        def depositions = cut.split(",").collect{
            Integer.parseInt(it)
        }

        return depositions
    }

    def getDepositionArray(SlideLayout layout)
    {
        //get deposition pattern as int array
        def intArray = parseDepositions(layout.depositionPattern)

        //we need an array to store deposition information in
        def neededLength = layout.columnsPerBlock * intArray.size()
        def depositionArray = new ArrayList(neededLength)

        //for each layout column we need to iterate over all depositions
        //this yields an array that allows for each real column to be assigned with the correct deposition number

        for (int layoutColumn = 1; layoutColumn <= layout.columnsPerBlock; layoutColumn++)
        {
            for(int d = 0; d < intArray.size(); d++)
                depositionArray[(intArray.size()) * (layoutColumn-1) + d] = intArray[d]
        }

        return depositionArray
    }

    def getDeposition(Spot spot, def depositionArray)
    {
        if(spot.layoutSpot == null) return null

        depositionArray[spot.col-1]
    }
}


