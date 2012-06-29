package org.nanocan.rppa.scanner

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import org.hibernate.ScrollableResults
import org.hibernate.ScrollMode

/**
 * Service handles the composition of all relevant spot information to create a CSV output file
 */
class SpotExportService {

    //dependencies
    def progressService
    def depositionService

    /**
     * Export spot information of a slide as convenient CSV file
     * @param slideInstance
     * @param params
     * @return
     */
    def exportToCSV(Slide slideInstance, def params) {

        def props = params.selectedProperties
        def depositionArray = depositionService.getDepositionArray(slideInstance.layout)

        def results = collectExportColumns(slideInstance, props, depositionArray, params)

        return fixDecimalSeparator(results, params.decimalSeparator, params.decimalPrecision)
    }

    /*
     * Query for spots
     */
    private def collectExportColumns(Slide slideInstance, props, depositionArray, params) {
        def spotTotal = slideInstance.spots.size()
        def spotCounter = 0

        def spots = Spot.findAllBySlide(slideInstance, [readOnly:true])

        def result = spots.collect { spot ->

            progressService.setProgressBarValue("${slideInstance.id}_CSVexport", spotCounter / spotTotal * 100)

            def spotPropList = new ArrayList<String>()

            if ("Block" in props) spotPropList << spot.block
            if ("Column" in props) spotPropList << spot.col
            if ("Row" in props) spotPropList << spot.row
            if ("FG" in props) spotPropList << spot.FG
            if ("BG" in props) spotPropList << spot.BG

            if ("Signal" in props) {
                if (params.excludeBadFlags == "on" && spot.flag != 0) spotPropList << "NA"
                else if (params.excludeBadDiameter == "on" && spot.diameter >= 250) spotPropList << "NA"
                else if (params.excludeBadSignal == "on" && spot.signal <= 0) spotPropList << "NA"
                else spotPropList << spot.signal
            }

            if ("x" in props) spotPropList << spot.x
            if ("y" in props) spotPropList << spot.y
            if ("Diameter" in props) spotPropList << spot.diameter
            if ("Flag" in props) spotPropList << spot.flag
            if ("Deposition" in props) spotPropList << depositionService.getDeposition(spot, depositionArray)
            if ("CellLine" in props) spotPropList << (spot.layoutSpot.cellLine ?: "NA")
            if ("LysisBuffer" in props) spotPropList << (spot.layoutSpot.lysisBuffer ?: "NA")
            if ("DilutionFactor" in props) spotPropList << (spot.layoutSpot.dilutionFactor?.dilutionFactor ?: "NA")
            if ("Inducer" in props) spotPropList << (spot.layoutSpot.inducer ?: "NA")
            if ("Treatment" in props) spotPropList << (spot.layoutSpot.treatment ?: "NA")
            if ("SpotType" in props) spotPropList << (spot.layoutSpot.spotType?.name ?: "NA")
            if ("SpotClass" in props) spotPropList << (spot.layoutSpot.spotType?.type ?: "NA")
            if ("SampleName" in props) spotPropList << (spot.layoutSpot.sample?.name ?: "NA")
            if ("SampleType" in props) spotPropList << (spot.layoutSpot.sample?.type ?: "NA")
            if ("TargetGene" in props) spotPropList << (spot.layoutSpot.sample?.targetGene ?: "NA")
        }

        return result
    }

    /*
     *  method that binds the blockshifts to the rest of the spot data
     */
    def includeBlockShifts(def spotCriteria, def slideInstance)
    {
        //get blockshiftMap
        def blockShiftMap = [:]

        BlockShift.findAllBySlide(slideInstance).each{
            blockShiftMap[it.blockNumber] = [it.horizontalShift, it.verticalShift]
        }

        spotCriteria.collect{ spot ->

            def array = []
            array.addAll(blockShiftMap[spot[0]]?:[0,0])
            array.addAll(spot)
            return(array)
        }
    }

    /*
     *  method that makes sure that the desired decimal separator is used for export
     */
    def fixDecimalSeparator(def spotCriteria, def decimalSeparator, def numberOfDecimals)
    {
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(decimalSeparator as char);
        df.setDecimalFormatSymbols(symbols);
        df.setGroupingUsed(false)

        spotCriteria.each {
            it.eachWithIndex{field, i ->
                if(field.toString().isDouble() && !field.toString().isInteger()) it[i] = df.format(field.round(Integer.parseInt(numberOfDecimals)))
            }
        }

        return spotCriteria
    }
}
