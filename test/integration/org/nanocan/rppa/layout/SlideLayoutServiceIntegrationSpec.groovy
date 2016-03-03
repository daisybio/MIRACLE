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
package org.nanocan.rppa.layout

import grails.plugin.spock.IntegrationSpec
import org.codehaus.groovy.grails.commons.cfg.ConfigurationHelper
import org.nanocan.layout.SlideLayout

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 15-06-12
 * Time: 10:55
 */
class SlideLayoutServiceIntegrationSpec extends IntegrationSpec{

    def slideLayoutService
    def grailsApplication

    def "create slide layout spots using groovy sql"(){
        setup:
        grailsApplication.config.rppa.jdbc.groovySql = true

        when:
        slideLayoutService.createSampleSpots(slideLayoutInstance)

        then:
        slideLayoutInstance.sampleSpots.size() == 2592

        where:
        slideLayoutInstance = new SlideLayout(columnsPerBlock: 6, rowsPerBlock: 12, numberOfBlocks: 36,
                title: "Test Layout", depositionPattern: "[4,4,2,2,1,1]").save(flush:true, failOnError: true)
    }

    def "create slide layout spots using GORM and hibernate"(){
        setup:
        grailsApplication.config.rppa.jdbc.groovySql = false

        when:
        slideLayoutService.createSampleSpots(slideLayoutInstance)

        then:
        slideLayoutInstance.sampleSpots.size() == 2592

        where:
        slideLayoutInstance = new SlideLayout(columnsPerBlock: 6, rowsPerBlock: 12, numberOfBlocks: 36,
                title: "New Test Layout", depositionPattern: "[4,4,2,2,1,1]").save(flush:true, failOnError: true)
    }
}
