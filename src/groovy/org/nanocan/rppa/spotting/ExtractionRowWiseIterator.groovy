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

import groovy.transform.InheritConstructors

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 18-10-12
 * Time: 09:41
 */
@InheritConstructors
class ExtractionRowWiseIterator extends ExtractionIteratorBase implements Iterator{

    @Override
    boolean hasNext() {
        if(currentExtractionRow > extractionRows || currentExtractionColumn > extractionColumns) return false
        else return true
    }

    @Override
    Object next() {
        def extraction = extract(currentExtractionColumn, currentExtractionRow)

        if(currentExtractionColumn == extractionColumns)
        {
            currentExtractionColumn = 1
            currentExtractionRow++
        }
        else currentExtractionColumn++

        return(extraction)
    }

    @Override
    void remove() {
    }
}