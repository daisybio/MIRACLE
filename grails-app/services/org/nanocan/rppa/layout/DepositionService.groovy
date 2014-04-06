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

import org.nanocan.layout.SlideLayout
import org.nanocan.rppa.scanner.Spot

/**
 * Service to handle the mapping of depositions between layouts and real slides
 */
class DepositionService {

    /**
     * Takes a deposition pattern from the domain class SlideLayout and turns it into an array
     * @param depositionPattern
     * @return
     */
    def parseDepositions(String depositionPattern)
    {
        //remove brackets and split by ,
        def cut = depositionPattern.substring(1, depositionPattern.length()-1)
        def depositions = cut.split(",").collect{
            Integer.parseInt(it)
        }

        return depositions
    }

    /**
     * Takes a layout and creates an array of depositions that fits the layout
     * @param layout
     * @return
     */
    def getDepositionArray(SlideLayout layout)
    {
        //get deposition pattern as int array
        def intArray = parseDepositions(layout.depositionPattern)

        //we need an array to store deposition information in
        def neededLength = layout.columnsPerBlock * intArray.size()
        def depositionArray = new ArrayList(neededLength)

        //for each layout column we need to iterate over all depositions
        //this yields an array that allows for each real column to be assigned with the correct deposition number

        for (int layoutColumn = 1; layoutColumn <= layout.columnsPerBlock; layoutColumn++)
        {
            for(int d = 0; d < intArray.size(); d++)
                depositionArray[(intArray.size()) * (layoutColumn-1) + d] = intArray[d]
        }

        return depositionArray
    }

    /**
     * Get the number of depositions for a specific spot using the array created by the method above
     * @param spot
     * @param depositionArray
     * @return
     */
    def getDeposition(Spot spot, def depositionArray)
    {
        if(spot.layoutSpot == null) return null

        depositionArray[spot.col-1]
    }
}
