package org.nanocan.rppa.layout

import org.nanocan.layout.ExtractionHead
import org.nanocan.plates.Plate
import org.nanocan.rppa.spotting.LeftToRightSpotter
import org.nanocan.rppa.spotting.TopToBottomSpotter
import org.nanocan.rppa.spotting.ExtractionRowWiseIterator
import org.nanocan.rppa.spotting.ExtractionColumnWiseIterator

class VirtualSpottingService {

   def progressService
   def grailsApplication

    /*
   We are assuming an extraction head with 48 pins, e.g. 12 columns and 4 rows. We could now extract with these 48 pins 8 extractions
   from a 384 well plate, either row- or column-wise (extractorOperationMode).
   We can then spot these again either row- or column-wise (or left-to-right and top-to-bottom in spottingOrientation more precisely) using the spot384 method.
   Since we are mainly dealing with 96 well plates that are diluted into a 384 well plate, we can also extract using a smaller extraction head (24 pins with 6x2)
   and use the method spot96as384 to multiply each well by four. We can customized the dilution pattern as well.

    1      2   becomes     1a  1b  2a 2b
   13     14               1c  1d  2c 2d
                           13a 13b 14a 14b
                           13c 13d 14c 14d

   One more issue is with respect to deposition patterns. We could generate a layout where each layout spot corresponds to a spot on the slide
   (allowing us to add more plates to a single slide),
   but deposition patterns occur in a regular fashion and thus allow us to merge these layout spots into one.
    */
    def importPlates(def settings) {

        def dilutionPattern = [[settings.topLeftDilution, settings.topRightDilution],
                [settings.bottomLeftDilution, settings.bottomRightDilution]]

        def spotter
        def matchingMaps = [:]
        matchingMaps.put("numberOfCellsSeeded", settings.numberOfCellsSeededMap?:[:])
        matchingMaps.put("cellLine", settings.cellLineMap?:[:])
        matchingMaps.put("inducer", settings.inducerMap?:[:])
        matchingMaps.put("treatment", settings.treatmentMap?:[:])
        matchingMaps.put("sample", settings.sampleMap?:[:])

        //spot top-to-bottom or left-to-right
        if(settings.spottingOrientation == "left-to-right") spotter = new LeftToRightSpotter(grailsApplication: grailsApplication, maxSpottingColumns: settings.xPerBlock, matchingMaps: matchingMaps, defaultLysisBuffer: settings.defaultLysisBuffer, defaultSpotType: settings.defaultSpotType)
        else if(settings.spottingOrientation == "top-to-bottom") spotter = new TopToBottomSpotter(grailsApplication: grailsApplication, maxSpottingRows: settings.xPerBlock, matchingMaps: matchingMaps, defaultLysisBuffer: settings.defaultLysisBuffer, defaultSpotType: settings.defaultSpotType)

        //percentage for progress bar
        def totalExtractions = settings.extractionCount
        def currentExtraction = 0

        //extraction head
        ExtractionHead extractor = ExtractionHead.get(settings.extractionHead)

        //iterate over plates
        settings.selectedLayouts.eachWithIndex{ obj, i ->

            int extractorRows = extractor.extractorRows
            int extractorCols = extractor.extractorColumns

            log.debug "iterating plate ${settings.layouts[obj]}"

            //iterate over extractions
            def iterator

            Plate plate = settings.layouts[obj]

            log.debug "Found plate ${plate}"

            //if we use a 384 well plate we can use the default options (48 pins), but in case of 96 well plates
            // we need to reduce the size of the virtual extraction head. We regain the lost pins by adding a dilution pattern during spotting with spot96as384.
            if(plate.format == "96-well" && settings.transformToThreeEightyFour)
            {
                log.debug "Plate type is 96 well, adjusting extraction head"
                extractorRows = extractorRows / 2
                extractorCols = extractorCols / 2
            }

            //extract row- or column-wise
            if(settings.extractorOperationMode == "row-wise") iterator = new ExtractionRowWiseIterator(plate: plate, extractorCols: extractorCols, extractorRows: extractorRows)
            else if(settings.extractorOperationMode =="column-wise") iterator = new ExtractionColumnWiseIterator(plate: plate, extractorCols: extractorCols, extractorRows: extractorRows)

            log.debug "iterator created for ${settings.extractorOperationMode}: ${iterator}"
            int extractionIndex = -1

            while(iterator.hasNext())
            {
                //update progress bar
                progressService.setProgressBarValue(settings.progressId, (((currentExtraction++) / totalExtractions) * 100))

                log.debug "iteration plate ${plate}"
                extractionIndex++
                log.debug settings.extractions.get((i+1).toString())
                log.debug settings.extractions[(i+1).toString()].get(extractionIndex)

                if(settings.extractions[(i+1).toString()].get(extractionIndex) == true){
                    log.debug "skipping extraction ${extractionIndex} of plate ${plate}"
                    iterator.next()
                    continue
                }

                //spot current extraction on virtual slide
                if(plate.format == "96-well" && settings.transformToThreeEightyFour)  {
                    log.debug "adding 96-well plate to slide layout as 384 diluted."
                    spotter.spot96as384(iterator.next(), dilutionPattern)
                }
                else if(plate.format == "96-well"){
                    log.debug "adding 96-well plate"
                    spotter.spot384(iterator.next())
                }
                else if(plate.format == "384-well"){
                    log.debug "adding 384-well plate."
                    spotter.spot384(iterator.next())
                }
                else{
                    throw new Exception("plate type unknown")
                    break;
                }
            }
            log.debug "done with plate ${plate}."
        }

        def slideLayout = spotter.slideLayout
        slideLayout.blocksPerRow = extractor.extractorColumns
        slideLayout.columnsPerBlock = settings.xPerBlock
        slideLayout.depositionPattern = settings.depositionPattern
        slideLayout.numberOfBlocks = extractor.extractorRows * extractor.extractorColumns
        slideLayout.rowsPerBlock = spotter.currentSpottingRow
        if(spotter instanceof LeftToRightSpotter && !spotter.currentSpottingRowUsed) slideLayout.rowsPerBlock--
        slideLayout.title = settings.title


        return(slideLayout)
    }
}

