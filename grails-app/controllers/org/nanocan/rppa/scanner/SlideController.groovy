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

import org.nanocan.file.ResultFileConfig
import org.springframework.dao.DataIntegrityViolationException

import org.apache.commons.io.FilenameUtils
import grails.plugins.springsecurity.Secured
import org.nanocan.project.Experiment
import org.nanocan.project.Project
import org.nanocan.layout.SlideLayout

@Secured(['ROLE_USER'])
class SlideController {

    //dependencies
    def springSecurityService
    def experimentService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    //dependencies
    def slideService
    def spotImportService
    def fileUploadService
    def progressService
    def securityTokenService

    /**
     * Standard controller actions
     */
    def index() {
        redirect(action: "list", params: params)
    }

    def customCSV(){
        if(params.selectedType == "custom") render template: "customCSV"
        else render ""
    }

    def list() {
        //deal with max
        if(!params.max && session.maxSlideResult) params.max = session.maxSlideResult
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        session.maxSlideResult = params.max

        //deal with offset
        params.offset = params.offset?:(session.offsetSlideResult?:0)
        session.offsetSlideResult = params.offset

        def slideInstanceList
        def slideInstanceListTotal

        if(session.slideLayoutSelected)
        {
            slideInstanceList = Slide.findAllByLayout(SlideLayout.get(session.slideLayoutSelected))
        }
        else if(session.experimentSelected)
        {
            slideInstanceList = []
            Experiment.get(session.experimentSelected).slideLayouts.each{
                slideInstanceList.addAll(Slide.findAllByLayout(it))
            }
            slideInstanceList.unique()
        }
        else if(session.projectSelected)
        {
            slideInstanceList = []
            def experiments = Experiment.findAllByProject(Project.get(session.projectSelected as Long))
            experiments.each{
                it.slideLayouts.each{ sl ->
                    slideInstanceList.addAll(Slide.findAllByLayout(sl))
                }
            }
            slideInstanceList.unique()
        }
        else
        {
            slideInstanceList = Slide.list(params)
            slideInstanceListTotal = Slide.count()
        }

        if (session.experimentSelected || session.projectSelected)
        {
            slideInstanceListTotal = slideInstanceList?.size()?:0
            if (params.int('offset') > slideInstanceListTotal) params.offset = 0

            if(slideInstanceListTotal > 0)
            {
                int rangeMin = Math.min(slideInstanceListTotal, params.int('offset'))
                int rangeMax = Math.min(slideInstanceListTotal, (params.int('offset') + params.int('max')))

                slideInstanceList = slideInstanceList.asList()

                if(params.sort == "antibody.name") slideInstanceList.sort{it.antibody}
                else if(params.sort == "experimenter.username") slideInstanceList.sort{it.experimenter.username}
                else if(params.sort == "resultFile.fileName") slideInstanceList.sort{it?.resultFile?.fileName?:""}
                else if(params.sort) slideInstanceList = slideInstanceList.sort{it[params.sort]}
                else slideInstanceList.sort{ a,b -> a.id <=> b.id}
                if(params.order == "desc") slideInstanceList = slideInstanceList.reverse()

                slideInstanceList = slideInstanceList.asList().subList(rangeMin, rangeMax)
            }
        }

        [slideInstanceList: slideInstanceList, slideInstanceTotal: slideInstanceListTotal]
    }

    def create() {
        params.experimenter = springSecurityService.currentUser
        [slideInstance: new Slide(params), experiments: Experiment.list()]
    }

    def copySlide(){
        def slideInstanceCopy = slideService.slideCopy(params)
        slideInstanceCopy.lastUpdatedBy = springSecurityService.currentUser
        slideInstanceCopy.createdBy = springSecurityService.currentUser
        slideInstanceCopy = fileUploadService.dealWithFileUploads(request, slideInstanceCopy)

        if (!slideInstanceCopy.save(flush: true)) {
            render(view: "create", model: [slideInstance: slideInstanceCopy])
            return
        }

        flash.message = message(code: 'default.copied.message', default: "Slide copied successfully", args: [message(code: 'slide.label', default: 'Slide'), slideInstanceCopy.id])
        redirect(action: "show", id: slideInstanceCopy.id)

    }

    def createFromTemplate(){
        def slideInstanceCopy = slideService.slideCopy(params)
        render(view: "create", model: [slideInstance: slideInstanceCopy] )
    }

    def save() {
        params.createdBy = springSecurityService.currentUser
        params.lastUpdatedBy = springSecurityService.currentUser

        def slideInstance = new Slide(params)

        slideInstance = fileUploadService.dealWithFileUploads(request, slideInstance)

        if (!slideInstance.save(flush: true, failOnError: true)) {
            render(view: "create", model: [slideInstance: slideInstance])
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'slide.label', default: 'Slide'), slideInstance.id])
        redirect(action: "show", id: slideInstance.id)
    }

    def show() {
        def slideInstance = Slide.get(params.id)
        if (!slideInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'slide.label', default: 'Slide'), params.id])
            redirect(action: "list")
            return
        }

        [slideInstance: slideInstance]
    }

    def edit() {
        def slideInstance = Slide.get(params.id)
        if (!slideInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'slide.label', default: 'Slide'), params.id])
            redirect(action: "list")
            return
        }

        [slideInstance: slideInstance]
    }

    def update() {

        def slideInstance = Slide.get(params.id)
        if (!slideInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'slide.label', default: 'Slide'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (slideInstance.version > version) {
                slideInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'slide.label', default: 'Slide')] as Object[],
                          "Another user has updated this Slide while you were editing")
                render(view: "edit", model: [slideInstance: slideInstance])
                return
            }
        }

        def oldResultFile = slideInstance.resultFile
        def oldLayout = slideInstance.layout

        params.lastUpdatedBy = springSecurityService.currentUser

        slideInstance.properties = params

        //deal with file uploads
        slideInstance = fileUploadService.dealWithFileUploads(request, slideInstance)

        //if result file or layout file have changed all spots and block shifts need to be deleted first
        if (slideInstance.spots.size() > 0 || slideInstance.blockShifts.size() > 0)
        {
            if (slideInstance.resultFile != oldResultFile || slideInstance.layout != oldLayout)
            {
                flash.message = "You can't change the result file or slide layout without deleting spots and block shift patterns first. This is necessary to keep the data consistent."
                render(view: "edit", model: [slideInstance: new Slide(params)])
                return
            }
        }

        if (!slideInstance.save(flush: true)) {
            render(view: "edit", model: [slideInstance: slideInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'slide.label', default: 'Slide'), slideInstance.id])
        redirect(action: "show", id: slideInstance.id)
    }

    def delete() {
        def slideInstance = Slide.get(params.id)
        if (!slideInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'slide.label', default: 'Slide'), params.id])
            redirect(action: "list")
            return
        }

        try {
            //remove from all experiments
            experimentService.updateExperiments(slideInstance, [])
            //delete spots first, saves a lot of time
            if (slideInstance?.spots?.size() > 0) spotImportService.deleteSpots(params.id)

            slideInstance.discard()
            Slide.get(params.id).delete(flush: true)

			flash.message = message(code: 'default.deleted.message', args: [message(code: 'slide.label', default: 'Slide'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'slide.label', default: 'Slide'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    /**
     * Additional controller actions
     */

    /* Add and delete spots, import from result file */
    def addSpots() {
        def slideInstance = Slide.get(params.id)

        def index = 1

        def fileEnding = FilenameUtils.getExtension(slideInstance.resultFile.filePath)

        def sheets = spotImportService.getSheets(slideInstance).collect{
            [index: index++, name: it]
        }

        [slideInstance: slideInstance, configs: ResultFileConfig.list(), fileEnding: fileEnding, sheets: sheets]
    }

    final ArrayList<String> spotProperties = ["block", "mainCol", "mainRow", "column", "row", "FG", "BG", "flag", "X", "Y", "diameter"]


    def readInputFile(){
        def slideInstance = Slide.get(params.id)

        //read sheet / file
        def sheetContent
        try{
            sheetContent = spotImportService.importSpotsFromFile(slideInstance.resultFile.filePath, params.int("sheet")?:0, params.int("minColRead")?:1)
        }catch(IOException e)
        {
            render "<div class='message'>Could not read file</div>"
            return
        }

        //convert CSV2 to CSV:
        if (params.csvType == "CSV2") sheetContent = spotImportService.convertCSV2(sheetContent)

        //convert custom CSV format to standard CSV:
        else if (params.csvType == "custom") sheetContent = spotImportService.convertCustomCSV(sheetContent, params.columnSeparator, params.decimalSeparator, params.thousandSeparator)

        //read config
        def resultFileCfg

        if(!params.config.equals("")){
            resultFileCfg = ResultFileConfig.get(params.config)
            slideInstance.lastConfig = resultFileCfg
            slideInstance.save()
        }
        def skipLines = resultFileCfg?.skipLines?:0
        if (params.skipLines == "on") skipLines = params.int("howManyLines")

        def header

        try{
            header = spotImportService.extractHeader(sheetContent, skipLines)
        }catch(NoSuchElementException e) {
            render "<div class='message'>Could not read header!</div>"
            return
        }

        //keeping content for later
        flash.totalSkipLines = ++skipLines
        flash.sheetContent = sheetContent

        //matching properties

        def matchingMap = spotImportService.createMatchingMap(resultFileCfg, header)

        render view: "assignFields", model: [progressId: "pId${params.id}", header: header, spotProperties: spotProperties, matchingMap: matchingMap]
    }


    def deleteSpots() {
        if(Slide.get(params.id)?.spots?.size() > 0)
            spotImportService.deleteSpots(params.id)

        redirect(action: "show", id: params.id)
    }

    def processResultFile() {

        def columnMap = [:]

        params.keySet().each{
            if(it.toString().startsWith("column") && it.toString() != "columnSeparator") columnMap.put(params.get(it), Integer.parseInt(it.toString().split('_')[1]))
        }

        def progressId = "pId" + params.id

        //check if minimal number of columns have been assigned
        if(columnMap.column == null || columnMap.row == null || (columnMap.block == null && (columnMap.mainRow == null || columnMap.mainCol == null))){
            render "row / column mapping missing."
            progressService.setProgressBarValue(progressId, 100)
            return;
        }
        if(columnMap.BG == null || columnMap.FG == null){
            render "FG and/or BG column mapping missing."
            progressService.setProgressBarValue(progressId, 100)
            return;
        }

        def slideInstance = Slide.get(params.id)

        progressService.setProgressBarValue(progressId, 0)

        if (slideInstance.spots.size() > 0) {
            render "this slide already contains spots. please delete them first!"
            progressService.setProgressBarValue(progressId, 100)
            return
        }
        def result = spotImportService.processResultFile(slideInstance, flash.sheetContent, columnMap, flash.totalSkipLines, progressId)

        progressService.setProgressBarValue(progressId, 100)

        if(!(result instanceof Slide)) render result

        else render "${slideInstance.spots.size()} spots have been added to the database and linked to the layout."
    }

    /* block shifts */
    def addBlockShifts () {
        def slideInstance = Slide.get(params.id)

        def hblockShifts = slideInstance.blockShifts.sort{ it.blockNumber }.collect{ it.horizontalShift }
        def vblockShifts = slideInstance.blockShifts.sort{ it.blockNumber }.collect{ it.verticalShift }

        [slideInstance: slideInstance, hblockShifts: hblockShifts, vblockShifts: vblockShifts]
    }

    def deleteBlockShifts () {
        def slideInstance = Slide.get(params.id)

        slideInstance.blockShifts.removeAll(slideInstance.blockShifts)

        if(slideInstance.save(flush: true)) {
            flash.message = "Block shifts deleted successfully"
        }
        else{
            flash.message = "Block shifts could not be deleted"
        }

        redirect(action: "show", id: params.id)
    }

    def saveBlockShiftPattern () {
        def slideInstance = Slide.get(params.id)

        def vshift = params.findAll{it.toString().startsWith("vshift")}
        def hshift = params.findAll{it.toString().startsWith("hshift")}

        slideInstance = slideService.persistBlockShiftPattern(slideInstance, hshift, vshift)

        if(slideInstance.save(flush: true))
        {
            flash.message = "Block shift pattern changed"
        }

        else flash.message = "Block shift pattern could NOT be changed!"

        redirect(action: "show", id: params.id)
    }

    def heatmapInIFrame(){
        def slideInstance = Slide.get(params.long("id"))
        def baseUrl = grailsApplication?.config?.shiny.baseUrl
        if(!baseUrl){
            baseUrl = g.createLink(controller: "spotExport", absolute: true).toString()
            baseUrl = baseUrl.substring(0, baseUrl.size()-5)
        }
        def spotExportLink = java.net.URLEncoder.encode(baseUrl, "UTF-8")
        def securityToken = java.net.URLEncoder.encode(securityTokenService.getSecurityToken(slideInstance), "UTF-8")

        def heatmapUrl = grailsApplication.config.shiny.heatmap + "?baseUrl=" + spotExportLink + "&securityToken=" + securityToken

        render """
            <iframe style="width: 700px; height: 900px;" frameBorder="0" src="${heatmapUrl}"/>
        """
    }

    def heatmap() {
        def slideInstance = Slide.get(params.long("id"))

        if(params.shiny == 'true')
        {
            def baseUrl = g.createLink(controller: "spotExport", absolute: true).toString()
            baseUrl = baseUrl.substring(0, baseUrl.size()-5)
            def spotExportLink = java.net.URLEncoder.encode(baseUrl, "UTF-8")
            def securityToken = java.net.URLEncoder.encode(securityTokenService.getSecurityToken(slideInstance), "UTF-8")

            def heatmapUrl = grailsApplication.config.shiny.heatmap + "?baseUrl=" + spotExportLink + "&securityToken=" + securityToken
            redirect(url: heatmapUrl)
        }
        else{
            def layout = slideInstance.layout
            def blockRows = layout.numberOfBlocks.intValue().intdiv(layout.blocksPerRow.intValue())
            [slideId: params.id, blockRows: blockRows, blocksPerRow: layout.blocksPerRow, rows: layout.rowsPerBlock,
             cols: layout.columnsPerBlock, slideInstance: slideInstance]
        }
    }
}
