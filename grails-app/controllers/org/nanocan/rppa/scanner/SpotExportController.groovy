package org.nanocan.rppa.scanner

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.nanocan.layout.LayoutSpot

@Secured(['ROLE_USER'])
class SpotExportController {

    def spotExportService

    def exportAsCSV = {
        def separatorMap = ["\t":"tab", ";":"semicolon", ",": "comma"]

        [slideInstanceId: params.id, separatorMap: separatorMap, slideProperties: csvHeader]
    }

    def exportAsJSON = {

        def slideInstance = Slide.get(params.id)
        def spots = slideInstance.spots

        render spots as JSON
    }

    def exportSpotsForHeatmapAsJSON = {

        def criteria = Spot.createCriteria()
        def result = criteria.list {
            eq("slide.id", params.long("id"))
            projections {
                property("id", "id")
                property("signal", "Signal")
                property("block", "Block")
                property("row", "Row")
                property("col", "Column")
            }
            order('block', 'asc')
            order('row', 'desc')
            order('col', 'asc')
        }

        result = [ data: result, meta: [
            "id": [type: "num"],
            "Signal": [type: "num"],
            "Block": [type: "cat"],
            "Row": [type: "num"],
            "Column": [type: "num"]
        ]]

        render result as JSON
    }

    def spotDetailsForHeatmap = {
        def spot = Spot.get(params.id)
        render spot as JSON
    }

    def exportShiftsAsJSON = {
        def slideInstance = Slide.get(params.id)
        def shifts = BlockShift.findAllBySlide(slideInstance)

        render shifts as JSON
    }

    def getDepositionPattern = {
        render Slide.get(params.id).layout.depositionPattern
    }

    def getBlocksPerRow = {
        render Slide.get(params.id).layout.blocksPerRow
    }

    def getTitle = {
        render Slide.get(params.id).title
    }

    def getIdFromBarcode = {
        render Slide.findAllByBarcode(params.id).id
    }

    def getAntibody = {
        render Slide.get(params.id).antibody.toString()
    }

    def createUrlForR = {

        //if we don't remove this, it'll override the action setting below
        params.remove("_action_createUrlForR")

        def baseUrl = g.createLink(controller: "spotExport", absolute: true).toString()
        baseUrl = baseUrl.substring(0, baseUrl.size()-5)

        [baseUrl: baseUrl, slideInstanceId: params.id]
    }

    def csvHeader = ["Block","Column","Row","FG","BG","Signal", "x","y","Diameter","Flag", "Deposition", "CellLine",
            "LysisBuffer", "DilutionFactor", "Inducer", "Treatment", "SpotType", "SpotClass", "SampleName", "SampleType",
            "TargetGene", "NumberOfCellsSeeded", "Replicate", "PlateRow", "PlateCol", "PlateLayout"]

    /*
     * action that triggers the actual creation of a csv file
     */
    def processExport = {

        def slideInstance = Slide.get(params.id)

        def results = spotExportService.exportToCSV(slideInstance, params)

        response.setHeader("Content-disposition", "filename=${slideInstance.toString().replace(" ", "_")}.csv")
        response.contentType = "application/vnd.ms-excel"

        def outs = response.outputStream

        def header = params.selectedProperties.join(params.separator)

        if(params.includeBlockShifts == "on")
        {
            results = spotExportService.includeBlockShifts(results, slideInstance)
            header = "hshift;vshift;" + header
        }

        outs << header
        outs << "\n"

        results.each() {

            outs << it.join(params.separator)
            outs << "\n"
        }
        outs.flush()
        outs.close()
        return
    }
}
