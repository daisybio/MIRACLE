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

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import groovy.json.JsonOutput
import org.codehaus.jackson.map.ObjectMapper
import org.hibernate.criterion.CriteriaSpecification
import org.json.simple.JSONArray
import org.nanocan.layout.LayoutSpot

class SpotExportController {

    def spotExportService
    def depositionService
    def springSecurityService
    def securityTokenService

    def meta = ["id", "Signal", "Block", "Row", "Column", "FG", "BG", "Flag", "Diameter",
                "SampleName", "SampleType", "TargetGene", "SampleGroupA", "SGADesc", "SampleGroupB", "SGBDesc", "SampleGroupC", "SGCDesc", "CellLine", "LysisBuffer", "DilutionFactor",
                "Inducer", "Treatment", "SpotType", "SpotClass", "NumberOfCellsSeeded", "Replicate", "PlateRow", "PlateCol", "Plate", "PlateLayout"]

    @Secured(['ROLE_USER'])
    def exportAsCSV = {
        def separatorMap = ["\t":"tab", ";":"semicolon", ",": "comma"]

        [slideInstanceId: params.id, separatorMap: separatorMap]
    }

    private def accessAllowed = { securityToken, slideInstance ->
        //check if user is authenticated
        if(!springSecurityService.isLoggedIn()){
            //alternatively check if a security token is provided
            if(!securityToken || securityToken != securityTokenService.getSecurityToken(slideInstance)){
                return(false)
            }

        }
        return(true)
    }


    def exportMetaDataAsJSON = {
        def slideInstance = Slide.get(params.id)

        if(accessAllowed(params.securityToken, slideInstance)){

            render meta as JSON
        }
        else{
            render status: 403
        }
    }

    def exportAsJSON = {
        def slideInstance = Slide.get(params.id)

        if(accessAllowed(params.securityToken, slideInstance)){
            def jsonResult = spotExportService.exportAsJSON(params.long("id"))

            response.contentType = "text/json"
            render jsonResult
        }
        else{
            render status: 403
        }
    }

    @Secured(['ROLE_USER'])
    def exportSpotsForSingleBlock(slideId, block){
        def criteria = Spot.createCriteria()
        def result = criteria.list {
            eq("slide.id", slideId)
            eq("block", block)
            projections {
                property("col", "Column")
                property("row", "Row")
                property("signal", "Signal")
                property("id", "id")
            }
            order('col', 'asc')
            order('row', 'asc')
        }

        //log2 transform and round signal
        result.each{
            if (it[2]==null) return
            else{
                it[2] = Math.round(it[2])
            }
        }

        result = result.collect{
            def obj = [:]
            obj.x = it[0]
            obj.y = it[1]
            obj.value = it[2]
            obj.id = it[3]
            return(obj)
        }

        return(result)
    }

    @Secured(['ROLE_USER'])
    def exportSpotsForBlocksAsJSON = {
        def slide = Slide.get(params.id)
        def blockRows = slide.layout.numberOfBlocks.intValue().intdiv(slide.layout.blocksPerRow.intValue())
        def blockCols = slide.layout.blocksPerRow
        def result = []

        for(row in 1..blockRows){
            for(column in 1..blockCols){
                def nextBlock = (row - 1) * blockCols + column
                result.add(exportSpotsForSingleBlock(slide.id, nextBlock))
            }
        }

        ObjectMapper mapper = new ObjectMapper()
        def jsonResult = mapper.writeValueAsString(result)

        response.contentType = "text/json"
        render jsonResult
    }

    @Secured(['ROLE_USER'])
    def exportSpotsForHeatmapAsJSON = {

        def criteria = Spot.createCriteria()

        def result = criteria.list {
            eq("slide.id", params.long("id"))
            projections {
                property("row", "Row")
                property("col", "Column")
                property("signal", "Signal")
                property("id", "id")
                property("block", "Block")
            }
            order('block', 'asc')
            order('row', 'asc')
            order('col', 'asc')
        }

        ObjectMapper mapper = new ObjectMapper()
        def jsonResult = mapper.writeValueAsString(result)

        response.contentType = "text/json"
        render jsonResult
    }

    @Secured(['ROLE_USER'])
    def spotDetailsForHeatmap = {
        def spot = Spot.get(params.id)
        def layout = spot.layoutSpot
        def depositionArray = depositionService.getDepositionArray(spot.layoutSpot.layout)
        render """Block/Column/Row/Deposition: ${spot.block}/${spot.col}/${spot.row}/${depositionService.getDeposition(spot, depositionArray)}<br/>
SampleName: ${layout.sample}<br/>
CellLine: ${layout.cellLine}<br/>
Inducer: ${layout.inducer}<br/>
LysisBuffer: ${layout.lysisBuffer}<br/>
Treatment: ${layout.treatment}<br/>
SpotType: ${layout.spotType}
"""
    }

    def isSecurityTokenValid = {
        if(Slide.findByUuid(params.id)) render true
        else render false
    }

    def getSlideIdFromSecurityToken = {
        def slideInstance = Slide.findByUuid(params.id)
        if(slideInstance){
            render slideInstance.id
        }
    }

    def exportShiftsAsJSON = {
        def slideInstance = Slide.get(params.id)

        if(accessAllowed(params.securityToken, slideInstance)){
            def shifts = BlockShift.findAllBySlide(slideInstance)

            render shifts as JSON
        }
        else{
            render status: 403
        }
    }

    def getDepositionPattern = {
        def slideInstance = Slide.get(params.id)

        if(accessAllowed(params.securityToken, slideInstance)){
            render slideInstance.layout.depositionPattern
        }
        else{
            render status: 403
        }
    }

    def getBlocksPerRow = {
        def slideInstance = Slide.get(params.id)

        if(accessAllowed(params.securityToken, slideInstance)){
            render slideInstance.layout.blocksPerRow
        }
        else{
            render status: 403
        }
    }

    def getTitle = {
        def slideInstance = Slide.get(params.id)

        if(accessAllowed(params.securityToken, slideInstance)){
            render slideInstance.title
        }
        else{
            render status: 403
        }
    }

    def getBarcode = {
        def slideInstance = Slide.get(params.id)

        if(accessAllowed(params.securityToken, slideInstance)){
            render slideInstance.barcode
        }
        else{
            render status: 403
        }
    }

    def getPMT = {
        def slideInstance = Slide.get(params.id)

        if(accessAllowed(params.securityToken, slideInstance)){
            render slideInstance.photoMultiplierTube
        }
        else{
            render status: 403
        }
    }

    def getIdFromBarcode = {
        render Slide.findAllByBarcode(params.id).id
    }

    def getAntibody = {
        def slideInstance = Slide.get(params.id)

        if(accessAllowed(params.securityToken, slideInstance)){
            render slideInstance.antibody.toString()
        }
        else{
            render status: 403
        }
    }

    @Secured(['ROLE_USER'])
    def createUrlForR = {

        //if we don't remove this, it'll override the action setting below
        params.remove("_action_createUrlForR")

        def baseUrl = g.createLink(controller: "spotExport", absolute: true).toString()
        baseUrl = baseUrl.substring(0, baseUrl.size()-5)
        def securityToken = securityTokenService.getSecurityToken(Slide.get(params.id))

        [baseUrl: baseUrl, slideInstanceId: params.id, securityToken: securityToken]
    }

    /*
     * action that triggers the actual creation of a csv file
     */
    @Secured(['ROLE_USER'])
    def processExport = {

        def slideInstance = Slide.get(params.id)

        def results = spotExportService.exportToCSV(slideInstance, params)

        def fileEnding = "csv"
        if(params.separator == "\t") fileEnding = "txt"

        response.setHeader("Content-disposition", "filename=${slideInstance.toString().replace(" ", "_")}.${fileEnding}")
        response.contentType = "application/vnd.ms-excel"

        def outs = response.outputStream

        def header = meta.join(params.separator)

        if(params.includeBlockShifts == "on")
        {
            results = spotExportService.includeBlockShifts(results, slideInstance)
            header = "hshift${params.separator}vshift${params.separator}" + header
        }

        def depositionArray = depositionService.getDepositionArray(slideInstance.layout)

        outs << "MIRACLE reverse phase protein array file format"
        outs << "\n"
        outs << "v.1.1"
        outs << "\n"
        outs << depositionArray//slideInstance.layout.depositionPattern?:""
        outs << "\n"
        outs << slideInstance.id
        outs << "\n"
        outs << slideInstance.barcode
        outs << "\n"
        outs << slideInstance.title
        outs << "\n"
        outs << slideInstance.antibody
        outs << "\n"
        outs << slideInstance.photoMultiplierTube
        outs << "("
        outs << slideInstance.photoMultiplierTubeSetting
        outs << ")"
        outs << "\n"
        outs << slideInstance.layout.blocksPerRow
        outs << "\n"
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
