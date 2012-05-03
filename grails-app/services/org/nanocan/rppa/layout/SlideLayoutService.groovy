package org.nanocan.rppa.layout

class SlideLayoutService {

    def createSampleSpots(SlideLayout slideLayout) {

        def depositions = getDepositionIntArray(slideLayout.depositionPattern)

        for (int block = 1; block <= slideLayout.numberOfBlocks; block++) {
            for (int col = 1; col <= slideLayout.columnsPerBlock; col++) {
                for (int row = 1; row <= slideLayout.rowsPerBlock; row++) {
                    slideLayout.addToSampleSpots(new LayoutSpot(block: block, column: col, row: row))
                }
            }
        }

        println slideLayout.save(flush: true, failOnError: true)
    }

    def getDepositionIntArray(String depositionPattern)
    {
        //remove brackets and split by ,
        String[] depositionPatternArray = depositionPattern.substring(1, depositionPattern.size()-1).split(",")
        def intArray = new int[depositionPatternArray.length]

        println depositionPatternArray


        for (int i = 0; i < depositionPatternArray.length; i++)
        {
            intArray[i] = Integer.parseInt(depositionPatternArray[i])
        }

        println intArray

        return intArray
    }
}


