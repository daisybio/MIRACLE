package org.nanocan.rppa.scanner

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.apache.commons.io.FilenameUtils

class SlideController {

    static navigation = [
            group: 'main',
            title: 'Slide Scanner Results'
    ]

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    //dependencies
    def slideService
    def spotImportService
    def spotExportService
    def fileUploadService
    def progressService

    /**
     * Standard controller actions
     */
    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [slideInstanceList: Slide.list(params), slideInstanceTotal: Slide.count()]
    }

    def create() {
        [slideInstance: new Slide(params)]
    }

    def save() {
        fileUploadService.dealWithFileUploads(request, params)

        def slideInstance = new Slide(params)

        if (!slideInstance.save(flush: true)) {
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

        def imagezoomFolder

        if(slideInstance?.resultImage)
            imagezoomFolder = FilenameUtils.removeExtension(slideInstance?.resultImage?.filePath.replace("upload", "imagezoom"))

        [slideInstance: slideInstance, imagezoomFolder: imagezoomFolder]
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

        //deal with file uploads
        fileUploadService.dealWithFileUploads(request, params)

        //if result file or layout file have changed all spots and block shifts need to be deleted first
        if (slideInstance.spots.size() > 0 || slideInstance.blockShifts.size() > 0)
        {
            if (slideInstance.resultFile != params.resultFile || slideInstance.layout != params.layout)
            {
                flash.message = "You can't change the result file or slide layout without deleting spots and block shift patterns first. This is necessary to keep the data consistent."
                render(view: "edit", model: [slideInstance: new Slide(params)])
                return
            }
        }

        slideInstance.properties = params

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
            //delete spots first, saves a lot of time
            spotImportService.deleteSpots()

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
    def addSpots = {
        def slideInstance = Slide.get(params.id)

        [slideInstance: slideInstance, configs: ResultFileConfig.list(), sheets: spotImportService.getSheets(slideInstance)]
    }

    def deleteSpots = {
        spotImportService.deleteSpots(params.id)

        redirect(action: "show", id: params.id)
    }

    def processResultFile = {

        def slideInstance = Slide.get(params.id)

        if (progressService.retrieveProgressBarValue("excelimport") != 0)
            render "an import process is already running. please try again later!"

        else if (slideInstance.spots.size() > 0)
            render "this slide already contains spots. please delete them first!"

        else if(!slideInstance.resultFile)
            render ("no result file found for ${slideInstance}")


        else if (!params.sheet)
            render "please select a sheet."

        else if (!params.config)
            render "please select a column map config to assign property columns correctly."

        else
        {
            def result = spotImportService.processResultFile(slideInstance, params.sheet, ResultFileConfig.get(params.config))

            progressService.setProgressBarValue("excelimport", 100)
            render result?:"${slideInstance.spots.size()} spots have been added to the database and linked to the layout."
        }
    }

    /* block shifts */
    def addBlockShifts = {
        def slideInstance = Slide.get(params.id)

        def hblockShifts = slideInstance.blockShifts.sort{ it.blockNumber }.collect{ it.horizontalShift }
        def vblockShifts = slideInstance.blockShifts.sort{ it.blockNumber }.collect{ it.verticalShift }

        [slideInstance: slideInstance, hblockShifts: hblockShifts, vblockShifts: vblockShifts]
    }

    def saveBlockShiftPattern = {
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
