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
package org.nanocan.rppa.spotting

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 18-10-12
 * Time: 10:25
 */
class ExtractionIteratorBase {

    def extractionRows
    def extractionColumns
    def currentExtractionRow
    def currentExtractionColumn
    def extractorRows
    def extractorCols
    def plate

    ExtractionIteratorBase(Map map){

        this.extractorCols = map.extractorCols?:12
        this.extractorRows = map.extractorRows?:4
        this.plate = map.plate

        //calculate number of extraction rows and columns
        extractionColumns = plate.cols / extractorCols
        extractionRows = plate.rows / extractorRows

        //set initial values
        currentExtractionColumn = 1
        currentExtractionRow = 1
    }

    def extract(int currentExtractionColumn, int currentExtractionRow){
        if(!plate) return null
        if(currentExtractionColumn > extractionColumns || currentExtractionRow > extractionRows) return null

        def startColumn = ((currentExtractionColumn - 1) * extractorCols) + 1
        def startRow = ((currentExtractionRow - 1) * extractorRows) + 1

        def endColumn = startColumn + extractorCols -1
        def endRow = startRow + extractorRows - 1

        def extraction


        extraction = org.nanocan.layout.WellLayout.withCriteria{
            plateLayout{
                eq("id", plate.plateLayout.id)
            }
            between("row", startRow, endRow)
            between("col", startColumn, endColumn)
        }

        return([extraction, plate.replicate, plate.id])
    }
}
