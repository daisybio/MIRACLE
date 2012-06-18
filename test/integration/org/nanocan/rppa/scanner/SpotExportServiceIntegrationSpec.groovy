package org.nanocan.rppa.scanner

import grails.plugin.spock.IntegrationSpec

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 14-06-12
 * Time: 15:19
 */
class SpotExportServiceIntegrationSpec extends IntegrationSpec {

    def spotExportService
    def spotImportService

    def "test CSV export with standard settings"(){
        given:
        if(slideInstance.spots == null)
        {
            spotImportService.processResultFile(slideInstance, sheetName, rfc)
        }

        when:
        def spotCriteria = spotExportService.exportToCSV(slideInstance, [selectedProperties: csvHeader,
            decimalSeparator: ",", decimalPrecision: "3",
            excludeBadFlags : "on",
            excludeBadDiameter : "on",
            excludeBadSignal : "on"])

        then:
        spotCriteria.size() == 5184

        where:
        slideInstance = Slide.get(3)
        sheetName = "b-tubulin (ab7792) PMT0"
        rfc = ResultFileConfig.findByName("Default Config")
        csvHeader = ["Block","Column","Row","FG","BG","Signal", "x","y","Diameter","Flag", "Deposition", "CellLine",
                "LysisBuffer", "DilutionFactor", "Inducer", "SpotType", "SpotClass", "SampleName", "SampleType", "TargetGene"]

    }
}
