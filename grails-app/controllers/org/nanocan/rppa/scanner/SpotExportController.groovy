package org.nanocan.rppa.scanner

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.codehaus.jackson.map.ObjectMapper
import org.json.simple.JSONArray
import org.nanocan.layout.LayoutSpot

class SpotExportController {

    def spotExportService
    def depositionService
    def springSecurityService
    def securityTokenService

    @Secured(['ROLE_USER'])
    def exportAsCSV = {
        def separatorMap = ["\t":"tab", ";":"semicolon", ",": "comma"]

        [slideInstanceId: params.id, separatorMap: separatorMap, slideProperties: csvHeader]
    }

    private def accessAllowed = { securityToken, uuid ->
        //check if user is authenticated
        if(!springSecurityService.isLoggedIn()){
            //alternatively check if a security token is provided
            if(!securityToken || securityToken != uuid){
                return(false)
            }

        }
        return(true)
    }

    def exportAsJSON = {
        def start = System.currentTimeMillis()
        def slideInstance = Slide.get(params.id)

/*
        returnArray['FG'] = it.FG
        returnArray['BG'] = it.BG
        returnArray['Signal'] = it.signal
        returnArray['Block'] = it.block
        returnArray['Row'] = it.row
        returnArray['Column'] = it.col
        returnArray['SampleName'] = it.layoutSpot?.sample?.name?:"NA"
        returnArray['SampleType'] = it.layoutSpot?.sample?.type?:"NA"
        returnArray['TargetGene'] = it.layoutSpot?.sample?.target?:"NA"
        returnArray['CellLine'] = it.layoutSpot?.cellLine?.name?:"NA"
        returnArray['LysisBuffer'] = it.layoutSpot?.lysisBuffer?.name?:"NA"
        returnArray['DilutionFactor'] = it.layoutSpot?.dilutionFactor?.dilutionFactor?:"NA"
        returnArray['Inducer'] = it.layoutSpot?.inducer?.name?:"NA"
        returnArray['Treatment'] = it.layoutSpot?.treatment?.name?:"NA"
        returnArray['SpotType'] = it.layoutSpot?.spotType?.name?:"NA"
        returnArray['SpotClass'] = it.layoutSpot?.spotType?.type?:"NA"
        returnArray['NumberOfCellsSeeded'] = it.layoutSpot?.numberOfCellsSeeded?.name?:"NA"
        returnArray['Replicate'] = it.layoutSpot?.replicate?:"NA"
        returnArray['PlateRow'] = it.layoutSpot?.wellLayout?.row?:"NA"
        returnArray['PlateCol'] = it.layoutSpot?.wellLayout?.col?:"NA"
        returnArray['PlateLayout'] = it.layoutSpot?.wellLayout?.plateLayout?.id?:"NA"
        returnArray['Flag'] = it.flag
        returnArray['Diameter'] = it.diameter*/

        if(accessAllowed(params.securityToken, slideInstance.uuid)){
            //def spots = slideInstance.spots

            def criteria = Spot.createCriteria()
            def result = criteria.list {
                eq("slide.id", params.long("id"))
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
                    layoutSpot{
                        sample{
                            property "name"
                            property "type"
                            property "target"
                        }
                        cellLine{
                            property "name"
                        }
                        lysisBuffer{
                            property "name"
                        }
                        dilutionFactor{
                            property "dilutionFactor"
                        }
                        inducer{
                            property "name"
                        }
                        treatment{
                            property "name"
                        }
                        spotType{
                            property "name"
                            property "type"
                        }
                        numberOfCellsSeeded{
                            property "name"
                        }
                        property "replicate"
                        wellLayout{
                            property "row"
                            property "col"
                            property "plateLayout.id"
                        }
                    }
                }
                order('block', 'asc')
                order('row', 'desc')
                order('col', 'asc')
            }
            /*def spots = criteria.list {
                eq("slide.id", params.long("id"))
                createAlias("layoutSpot", "lspot")
                order('block', 'asc')
                order('row', 'desc')
                order('col', 'asc')
            } */

            def end = System.currentTimeMillis()
            println "dbtime: " + ((end - start) / 1000)

            ObjectMapper mapper = new ObjectMapper()
            def jsonResult = mapper.writeValueAsString(result)

            //def result = spots.collect{ mapper.writeValueAsString(it) }

            //println result
            response.contentType = "text/json"
            render jsonResult

            /*
            render(contentType: "text/json"){[
                    meta: ["id", "Signal", "Block", "Row", "Column", "FG", "BG", "Flag", "Diameter",
                            "SampleName", "SampleType", "TargetGene", "CellLine", "LysisBuffer", "DilutionFactor",
                            "Inducer", "Treatment", "SpotType", "SpotClass", "NumberOfCellsSeeded", "Replicate", "PlateRow", "PlateCol", "PlateLayout"] as JSONArray,
                    data: jsonResult,
                    status : jsonResult ? "OK" : "Nothing present"
            ]}*/

            end = System.currentTimeMillis()
            println "json parsing time: " + ((end - start) / 1000)
        }
        else{
            render status: 403
        }
    }

    @Secured(['ROLE_USER'])
    def exportSpotsForHeatmapAsJSON = {

        def criteria = Spot.createCriteria()
        def result = criteria.list {
            eq("slide.id", params.long("id"))
            projections {
                property("id", "id")
                property("signal", "Signal")
                property("block", "Block")
                property("row", "Row")
                property("col", "Column")
            }
            order('block', 'asc')
            order('row', 'desc')
            order('col', 'asc')
        }

        //log2 transform and round signal
        result.each{
            if(it[1]==0) it[1]=null
            else if (it[1]==null) return
            else{
               it[1] = Math.round(Math.log(it[1])/Math.log(2))
            }
        }

        result = [ data: result, meta: [
            "id": [type: "num"],
            "Signal": [type: "num"],
            "Block": [type: "cat"],
            "Row": [type: "num"],
            "Column": [type: "num"]
        ]]

        render result as JSON
    }

    @Secured(['ROLE_USER'])
    def spotDetailsForHeatmap = {
        def spot = Spot.get(params.id)
        def layout = spot.layoutSpot
        def depositionArray = depositionService.getDepositionArray(spot.layoutSpot.layout)
        render """B/C/R: ${spot.block}/${spot.col}/${spot.row}
Deposition: ${depositionService.getDeposition(spot, depositionArray)}
SampleName: ${layout.sample}
CellLine: ${layout.cellLine}
Inducer: ${layout.inducer}
LysisBuffer: ${layout.lysisBuffer}
Treatment: ${layout.treatment}
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

        if(accessAllowed(params.securityToken, slideInstance.uuid)){
            def shifts = BlockShift.findAllBySlide(slideInstance)

            render shifts as JSON
        }
        else{
            render status: 403
        }
    }

    def getDepositionPattern = {
        def slideInstance = Slide.get(params.id)

        if(accessAllowed(params.securityToken, slideInstance.uuid)){
            render slideInstance.layout.depositionPattern
        }
        else{
            render status: 403
        }
    }

    def getBlocksPerRow = {
        def slideInstance = Slide.get(params.id)

        if(accessAllowed(params.securityToken, slideInstance.uuid)){
            render slideInstance.layout.blocksPerRow
        }
        else{
            render status: 403
        }
    }

    def getTitle = {
        def slideInstance = Slide.get(params.id)

        if(accessAllowed(params.securityToken, slideInstance.uuid)){
            render slideInstance.title
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

        if(accessAllowed(params.securityToken, slideInstance.uuid)){
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

    def csvHeader = ["Block","Column","Row","FG","BG","Signal", "x","y","Diameter","Flag", "Deposition", "CellLine",
            "LysisBuffer", "DilutionFactor", "Inducer", "Treatment", "SpotType", "SpotClass", "SampleName", "SampleType",
            "TargetGene", "NumberOfCellsSeeded", "Replicate", "PlateRow", "PlateCol", "PlateLayout"]

    /*
     * action that triggers the actual creation of a csv file
     */
    @Secured(['ROLE_USER'])
    def processExport = {

        def slideInstance = Slide.get(params.id)

        def results = spotExportService.exportToCSV(slideInstance, params)

        response.setHeader("Content-disposition", "filename=${slideInstance.toString().replace(" ", "_")}.csv")
        response.contentType = "application/vnd.ms-excel"

        def outs = response.outputStream

        def header = params.selectedProperties.join(params.separator)

        if(params.includeBlockShifts == "on")
        {
            results = spotExportService.includeBlockShifts(results, slideInstance)
            header = "hshift;vshift;" + header
        }

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
