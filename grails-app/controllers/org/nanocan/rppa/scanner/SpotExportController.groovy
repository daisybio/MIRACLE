package org.nanocan.rppa.scanner

class SpotExportController {

    def spotExportService

    def exportAsCSV = {
        def separatorMap = ["\t":"tab", ";":"semicolon", ",": "comma"]

        [slideInstanceId: params.id, separatorMap: separatorMap, slideProperties: csvHeader]
    }

    def createUrlForR = {

        println params
        params.remove("_action_createUrlForR")
        def exportLink = g.createLink(controller: "spotExport", action: "processExport", params:  params, absolute: true)

        def importCommand = """read.table("${exportLink.replaceAll("&", "\n&")}", header = TRUE,
            sep = "${params.separator}", dec = "${params.decimalSeparator}")"""

        [exportLink: exportLink, importCommand: importCommand, slideInstanceId: params.id]
    }

    def csvHeader = ["Block","Column","Row","FG","BG","Signal", "x","y","Diameter","Flag", "Deposition", "CellLine",
            "LysisBuffer", "DilutionFactor", "Inducer", "SpotType", "SpotClass", "SampleName", "SampleType", "TargetGene"]

    /*
     * action that triggers the actual creation of a csv file
     */
    def processExport = {

        def slideInstance = Slide.get(params.id)

        def results = spotExportService.exportToCSV(slideInstance, params)

        response.setHeader("Content-disposition", "filename=${slideInstance}.csv")
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
