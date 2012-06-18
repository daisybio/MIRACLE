package org.nanocan.rppa.layout

import org.nanocan.rppa.scanner.Spot

/**
 * Service to handle the mapping of depositions between layouts and real slides
 */
class DepositionService {

    /**
     * Takes a deposition pattern from the domain class SlideLayout and turns it into an array
     * @param depositionPattern
     * @return
     */
    def parseDepositions(String depositionPattern)
    {
        //remove brackets and split by ,
        def cut = depositionPattern.substring(1, depositionPattern.length()-1)
        def depositions = cut.split(",").collect{
            Integer.parseInt(it)
        }

        return depositions
    }

    /**
     * Takes a layout and creates an array of depositions that fits the layout
     * @param layout
     * @return
     */
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

    /**
     * Get the number of depositions for a specific spot using the array created by the method above
     * @param spot
     * @param depositionArray
     * @return
     */
    def getDeposition(Spot spot, def depositionArray)
    {
        if(spot.layoutSpot == null) return null

        depositionArray[spot.col-1]
    }
}
