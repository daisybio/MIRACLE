package org.nanocan.rppa.scanner

class SpotExportController {

    /* export to csv file */
    def exportAsCSV = {

        def slideInstance = Slide.get(params.id)
        def separatorMap = ["\t":"tab", ";":"semicolon", ",": "comma"]

        [slideInstance: slideInstance, separatorMap: separatorMap, slideProperties: csvHeader]
    }

    def createUrlForR = {
        render params.toString()
    }

    def csvHeader = ["Block","Column","Row","FG","BG","Signal", "x","y","Diameter","Flag", "Deposition", "CellLine",
            "LysisBuffer", "DilutionFactor", "Inducer", "SpotType", "SpotClass", "SampleName", "SampleType", "TargetGene"]

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
