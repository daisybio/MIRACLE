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
package org.nanocan.rppa.io

import org.codehaus.jackson.map.ObjectMapper
import org.hibernate.criterion.CriteriaSpecification

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import org.nanocan.rppa.scanner.Slide
import org.nanocan.rppa.scanner.Spot
import org.nanocan.rppa.scanner.BlockShift

/**
 * Service handles the composition of all relevant spot information to create a CSV output file
 */
class SpotExportService {

    //dependencies
    def progressService
    def depositionService

    /**
     * Export spot information of a slide as convenient CSV file
     * @param slideInstance
     * @param params
     * @return
     */
    def exportToCSV(Slide slideInstance, def params) {

        def results = getFormatedSpotsFromDatabase(slideInstance.id)

        return fixDecimalSeparator(results, params.decimalSeparator, params.decimalPrecision)
    }

    private def getFormatedSpotsFromDatabase(def id)
    {
        def criteria = Spot.createCriteria()
        def result = criteria.list {
            eq("slide.id", id)
            createAlias('layoutSpot', 'lSpot', CriteriaSpecification.LEFT_JOIN)
            createAlias('lSpot.sample', 'smpl', CriteriaSpecification.LEFT_JOIN)
            createAlias('lSpot.cellLine', 'cline', CriteriaSpecification.LEFT_JOIN)
            createAlias('lSpot.lysisBuffer', 'lbuffer', CriteriaSpecification.LEFT_JOIN)
            createAlias('lSpot.dilutionFactor', 'dilfactor', CriteriaSpecification.LEFT_JOIN)
            createAlias('lSpot.inducer', 'indcr', CriteriaSpecification.LEFT_JOIN)
            createAlias('lSpot.treatment', 'trtmnt', CriteriaSpecification.LEFT_JOIN)
            createAlias('lSpot.spotType', 'sptype', CriteriaSpecification.LEFT_JOIN)
            createAlias('lSpot.numberOfCellsSeeded', 'numberOfCells', CriteriaSpecification.LEFT_JOIN)
            createAlias('lSpot.wellLayout', 'well', CriteriaSpecification.LEFT_JOIN)
            projections {
                property "id"
                property "signal"
                property "block"
                property "row"
                property "col"
                property "FG"
                property "BG"
                property "flag"
                property "diameter"
                property "smpl.name"
                property "smpl.type"
                property "smpl.target"
                property "smpl.sampleGroupA"
                property "smpl.sampleGroupADescription"
                property "smpl.sampleGroupB"
                property "smpl.sampleGroupBDescription"
                property "smpl.sampleGroupC"
                property "smpl.sampleGroupCDescription"
                property "cline.name"
                property "lbuffer.name"
                property "dilfactor.dilutionFactor"
                property "indcr.name"
                property "trtmnt.name"
                property "sptype.name"
                property "sptype.type"
                property "numberOfCells.name"
                property "lSpot.replicate"
                property "well.row"
                property "well.col"
                property "lSpot.plate.id"
                property "well.plateLayout.id"
            }
            order('block', 'asc')
            order('row', 'desc')
            order('col', 'asc')
        }
        return(result)
    }

    def exportAsJSON(def id)
    {
        def result = getFormatedSpotsFromDatabase(id)

        ObjectMapper mapper = new ObjectMapper()
        def jsonResult = mapper.writeValueAsString(result)

        return(jsonResult)
    }

    /*
     *  method that binds the blockshifts to the rest of the spot data
     */
    def includeBlockShifts(def spotCriteria, def slideInstance)
    {
        //get blockshiftMap
        def blockShiftMap = [:]

        BlockShift.findAllBySlide(slideInstance).each{
            blockShiftMap[it.blockNumber] = [it.horizontalShift, it.verticalShift]
        }

        spotCriteria.collect{ spot ->

            def array = []
            array.addAll(blockShiftMap[spot[0]]?:[0,0])
            array.addAll(spot)
            return(array)
        }
    }

    /*
     *  method that makes sure that the desired decimal separator is used for export
     */
    def fixDecimalSeparator(def spotCriteria, def decimalSeparator, def numberOfDecimals)
    {
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(decimalSeparator as char);
        df.setDecimalFormatSymbols(symbols);
        df.setGroupingUsed(false)

        spotCriteria.each {
            it.eachWithIndex{field, i ->
                if(field.toString().isDouble() && !field.toString().isInteger()) it[i] = df.format(field.round(Integer.parseInt(numberOfDecimals)))
            }
        }

        return spotCriteria
    }
}
