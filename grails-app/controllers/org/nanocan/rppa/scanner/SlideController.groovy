package org.nanocan.rppa.scanner

import org.springframework.dao.DataIntegrityViolationException

import org.apache.commons.io.FilenameUtils
import grails.plugins.springsecurity.Secured
import org.nanocan.rppa.project.Project

@Secured(['ROLE_USER'])
class SlideController {

    //dependencies
    def springSecurityService
    def projectService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    //dependencies
    def slideService
    def spotImportService
    def fileUploadService
    def progressService

    /**
     * Standard controller actions
     */
    def index() {
        redirect(action: "list", params: params)
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

        if(session.projectSelected)
        {
            slideInstanceList = Project.get(session.projectSelected as Long).slides
            slideInstanceListTotal = slideInstanceList.size()

            int rangeMin = Math.min(slideInstanceListTotal, params.int('offset'))
            int rangeMax = Math.min(slideInstanceListTotal, (params.int('offset') + params.int('max')))

            slideInstanceList = slideInstanceList.asList().subList(rangeMin, rangeMax)
        }

        else
        {
            slideInstanceList = Slide.list(params)
            slideInstanceListTotal = Slide.count()
        }

        [slideInstanceList: slideInstanceList, slideInstanceTotal: slideInstanceListTotal]
    }

    def create() {
        params.experimenter = springSecurityService.currentUser
        [slideInstance: new Slide(params), projects: Project.list()]
    }

    def save() {
        params.createdBy = springSecurityService.currentUser
        params.lastUpdatedBy = springSecurityService.currentUser

        def slideInstance = new Slide(params)

        slideInstance = fileUploadService.dealWithFileUploads(request, slideInstance)

        if (!slideInstance.save(flush: true, failOnError: true)) {
            render(view: "create", model: [slideInstance: slideInstance])
        }
        projectService.addToProject(slideInstance, params.projectsSelected)

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

        def imagezoomFolder
        boolean imagezoomFolderExists

        if(slideInstance?.resultImage)
        {
            imagezoomFolder = fileUploadService.getImagezoomTarget(slideInstance?.resultImage?.filePath)
            imagezoomFolderExists = new File(grailsApplication.config.rppa.imagezoom.directory + "/" + fileUploadService.getImagezoomFolder(slideInstance?.resultImage?.filePath)).exists()
        }

        [slideInstance: slideInstance,imagezoomFolderExists: imagezoomFolderExists, imagezoomFolder: imagezoomFolder, projects:  projectService.findProject(slideInstance)]
    }

    def edit() {
        def slideInstance = Slide.get(params.id)
        if (!slideInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'slide.label', default: 'Slide'), params.id])
            redirect(action: "list")
            return
        }

        [slideInstance: slideInstance, projects: Project.list(), selectedProjects: projectService.findProject(slideInstance)]
    }

    def zoomifyImage() {
        def slideInstance = Slide.get(params.id)
        fileUploadService.zoomifyImage(slideInstance.resultImage.filePath)

        def imagezoomFolder

        if(slideInstance?.resultImage)
        {
            imagezoomFolder = fileUploadService.getImagezoomTarget(slideInstance?.resultImage?.filePath)
        }

        render template: "slideZoom", model: [imagezoomFolder: imagezoomFolder]
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

        projectService.updateProjects(slideInstance, params.projectsSelected)

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
            //remove from all projects
            projectService.updateProjects(slideInstance, [])
            //delete spots first, saves a lot of time
            if (slideInstance?.spots?.size() > 0) spotImportService.deleteSpots()

            slideInstance.delete(flush: true)
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

    final ArrayList<String> spotProperties = ["block", "column", "row", "FG", "BG", "flag", "X", "Y", "diameter"]


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
            if(it.toString().startsWith("column")) columnMap.put(params.get(it), Integer.parseInt(it.toString().split('_')[1]))
        }

        def slideInstance = Slide.get(params.id)

        def progressId = "pId" + params.id

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


}
