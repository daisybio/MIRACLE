package org.nanocan.savanah

import org.nanocan.savanah.plates.Plate
import org.nanocan.savanah.extraction.ExtractionRowWiseIterator
import org.nanocan.savanah.extraction.ExtractionColumnWiseIterator
import org.nanocan.rppa.spotting.Spotter

class PlateImportService {

    def importPlates(plates, extractions, spottingOrientation, extractorOperationMode, depositionPattern, columnsPerBlock, bottomLeftDilution, topLeftDilution, topRightDilution, bottomRightDilution) {

        def spotter = new Spotter()

        //iterate over plates
        plates.each{
            //iterate over extractions
            def iterator

            if(extractorOperationMode == "row-wise") iterator = new ExtractionRowWiseIterator(plate: Plate.findByName(plate))
            else if(extractorOperationMode =="column-wise") iterator = new ExtractionColumnWiseIterator(plate: Plate.findByName(plate))

            while(iterator.hasNext())
            {
                //spot current extraction on virtual slide
                spotter.spot(iterator.next())
            }
        }

        //check if plates need conversion
        def convertedPlates = plates.collect{
            if(Plate.findByName(it).format == "384-well") return it
            else return(convert96to384(it))
        }
    }
}
