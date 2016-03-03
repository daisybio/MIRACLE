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

import grails.plugin.spock.UnitSpec
import org.nanocan.layout.SlideLayout

class DepositionServiceSpec extends UnitSpec{

    def "parse a deposition pattern"(){

        setup:
        def depositionService = new DepositionService()

        when:
        def intArray = depositionService.parseDepositions(depositionPattern)

        then:
        intArray == [4, 4, 2, 2, 1, 1]

        where:
        depositionPattern = "[4,4,2,2,1,1]"
    }

    def "get a deposition array for a 2 columns per block layout"(){

        setup:
        def depositionService = new DepositionService()
        mockDomain(SlideLayout)
        def layout = new SlideLayout(depositionPattern: "[4,4,2,2,1,1]", columnsPerBlock: 2)

        when:
        def fullArray = depositionService.getDepositionArray(layout)

        then:
        fullArray == [4, 4, 2, 2, 1, 1, 4, 4, 2, 2, 1, 1]

        where:
        depositionPattern = "[4,4,2,2,1,1]"
    }
}
