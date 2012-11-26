package org.nanocan.savanah

import org.nanocan.savanah.plates.Plate
import org.nanocan.savanah.extraction.ExtractionRowWiseIterator
import org.nanocan.savanah.extraction.ExtractionColumnWiseIterator
import org.nanocan.rppa.spotting.LeftToRightSpotter
import org.nanocan.rppa.spotting.TopToBottomSpotter
import org.nanocan.rppa.layout.Dilution
import org.nanocan.rppa.layout.PlateLayout

class PlateImportService {

    def grailsApplication

    def createMatchListsForSavanahLayouts(List<PlateLayout> plateLayouts) {
        def numberOfCellsSeededList = []
        def cellLineList = []
        def inducerList = []
        def treatmentList = []
        def sampleList = []

        plateLayouts.each { playout ->
            playout.wells.each {
                if (it.numberOfCellsSeeded) numberOfCellsSeededList << it.numberOfCellsSeeded
                if (it.cellLine) cellLineList << it.cellLine
                if (it.inducer) inducerList << it.inducer
                if (it.treatment) treatmentList << it.treatment
                if (it.sample) sampleList << it.sample
            }
        }

        numberOfCellsSeededList.unique()
        cellLineList.unique()
        inducerList.unique()
        treatmentList.unique()
        sampleList.unique()

        def titles = plateLayouts.collect {it.name}

        [numberOfCellsSeededList: numberOfCellsSeededList, cellLineList: cellLineList,
                inducerList: inducerList, treatmentList: treatmentList, sampleList: sampleList,
                titles: titles, plateLayouts: plateLayouts]
    }

    def getPlateLayoutFromId(it, layouts) {
        if (it.toString().startsWith("savanah")) {
            def layout = org.nanocan.savanah.plates.PlateLayout.get(it.toString().substring(7))
            layouts.put(it, layout)
        }
        else {
            def layout = org.nanocan.rppa.layout.PlateLayout.get(it.toString().substring(7))
            layouts.put(it, layout)
        }

        return(layouts)
    }

    def importPlateLayout(def plateName, def newPlateName, def cellLineMap, def numberOfCellsMap,def inducerMap, def treatmentMap, def sampleMap){
        def savanahLayout = org.nanocan.savanah.plates.PlateLayout.findByName(plateName)

        def miracleLayout = new org.nanocan.rppa.layout.PlateLayout(name: newPlateName, format: savanahLayout.format).save(flush: true, failOnError: true)
        savanahLayout.wells.each{ well ->
            miracleLayout.addToWells(new org.nanocan.rppa.layout.WellLayout(col:  well.col, row: well.row, cellLine: cellLineMap."${well.cellLine}",
                    inducer: inducerMap."${well.inducer}", numberOfCellsSeeded: numberOfCellsMap."${well.numberOfCellsSeeded}",
                    treatment: treatmentMap."${well.treatment}", spotType: null, sample: sampleMap."${well.sample}"))
        }

        miracleLayout.save(flush: true, failOnError: true)

        return miracleLayout
    }

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
        matchingMaps.put("cellLineMap", settings.cellLineMap?:[:])
        matchingMaps.put("inducerMap", settings.inducerMap?:[:])
        matchingMaps.put("treatmentMap", settings.treatmentMap?:[:])
        matchingMaps.put("sampleMap", settings.sampleMap?:[:])

        //spot top-to-bottom or left-to-right
        if(settings.spottingOrientation == "left-to-right") spotter = new LeftToRightSpotter(grailsApplication: grailsApplication, maxSpottingColumns: settings.xPerBlock, matchingMaps: matchingMaps)
        else if(settings.spottingOrientation == "top-to-bottom") spotter = new TopToBottomSpotter(grailsApplication: grailsApplication, maxSpottingRows: settings.xPerBlock, matchingMaps: matchingMaps)

        //iterate over plates
        settings.layouts.each{ key, it ->

            log.debug "iterating plate ${it}"

            //iterate over extractions
            def iterator
            int extractorRows = 4
            int extractorCols = 12

            def plateLayout = it
            log.debug "Found plate layout ${plateLayout}"

            //if we use a 384 well plate we can use the default options (48 pins), but in case of 96 well plates
            // we need to reduce the size of the virtual extraction head. We regain the lost pins by adding a dilution pattern during spotting with spot96as384.
            if(plateLayout.format == "96-well")
            {
                log.debug "Plate type is 96 well, adjusting extraction head"
                extractorRows = extractorRows / 2
                extractorCols = extractorCols / 2
            }

            //extract row- or column-wise
            if(settings.extractorOperationMode == "row-wise") iterator = new ExtractionRowWiseIterator(plateLayout: plateLayout, extractorCols: extractorCols, extractorRows: extractorRows)
            else if(settings.extractorOperationMode =="column-wise") iterator = new ExtractionColumnWiseIterator(plateLayout: plateLayout, extractorCols: extractorCols, extractorRows: extractorRows)

            log.debug "iterator created for ${settings.extractorOperationMode}: ${iterator}"
            int extractionIndex = -1

            while(iterator.hasNext())
            {
                log.debug "iteration plate layout ${plateLayout}"
                extractionIndex++
                log.debug settings.extractions.get(key)
                log.debug settings.extractions[key].get(extractionIndex)

                if(settings.extractions[key].get(extractionIndex) == true){
                    log.debug "skipping extraction ${extractionIndex} of plate layout ${it}"
                    iterator.next()
                    continue
                }

                //spot current extraction on virtual slide
                if(plateLayout.format == "96-well")  {
                    log.debug "adding 96-well plate layout to slide layout as 384 diluted."
                    spotter.spot96as384(iterator.next(), dilutionPattern)
                }
                else if(plateLayout.format == "384-well"){
                    log.debug "adding 384-well plate layout."
                    spotter.spot384(iterator.next())
                }
                else{
                    throw new Exception("plate type unknown")
                    break;
                }
            }
            log.debug "done with plate layout ${plateLayout}."
        }

        def slideLayout = spotter.slideLayout
        slideLayout.blocksPerRow = 12
        slideLayout.columnsPerBlock = settings.xPerBlock
        slideLayout.depositionPattern = settings.depositionPattern
        slideLayout.numberOfBlocks = 48
        slideLayout.rowsPerBlock = spotter.currentSpottingRow
        if(spotter instanceof LeftToRightSpotter && !spotter.currentSpottingRowUsed) slideLayout.rowsPerBlock--
        slideLayout.title = settings.title

        return(slideLayout)
    }
}
