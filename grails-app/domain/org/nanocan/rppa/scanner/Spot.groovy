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

import org.nanocan.layout.LayoutSpot

class Spot {

    double FG
    double BG
    double signal
    int x
    int y
    int diameter
    int flag
    int block
    int row
    int col

    static mapping = {
        slide index: 'spot_idx'
        block index: 'spot_idx'
        col index:  'spot_idx'
        row index: 'spot_idx'
    }

    static belongsTo = [slide: Slide, layoutSpot: LayoutSpot]

    static constraints = {
        slide(unique: ['col', 'row', 'block'])
        layoutSpot nullable: true
    }

    String toString()
    {
        "B/C/R: ${block}/${col}/${row}, FG/BG: ${FG}/${BG}, flag: ${flag}"
    }

    def beforeInsert(){
        signal = FG - BG
    }

    def beforeUpdate()
    {
        signal = FG - BG
    }
}
