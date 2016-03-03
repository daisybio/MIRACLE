/*
 * Copyright (C) 2014
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:			http://www.nanocan.org/miracle/
 * ###########################################################################
 *	
 *	This file is part of MIRACLE.
 *
 *  MIRACLE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
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
