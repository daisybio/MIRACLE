package org.nanocan.rppa.layout

import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_USER'])
class PlateLayoutController {

    def plateLayoutService
    def progressService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [plateLayoutInstanceList: PlateLayout.list(params), plateLayoutInstanceTotal: PlateLayout.count()]
    }

    def create() {
        [plateLayoutInstance: new PlateLayout(params)]
    }

    def save() {
        def plateLayoutInstance = new PlateLayout(params)
        if (!plateLayoutInstance.save(flush: true)) {
            render(view: "create", model: [plateLayoutInstance: plateLayoutInstance])
            return
        }

        plateLayoutService.createWellLayouts(plateLayoutInstance)

		flash.message = message(code: 'default.created.message', args: [message(code: 'plateLayout.label', default: 'PlateLayout'), plateLayoutInstance.id])
        redirect(action: "show", id: plateLayoutInstance.id)
    }

    def show() {
        def plateLayoutInstance = PlateLayout.get(params.id)
        if (!plateLayoutInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'plateLayout.label', default: 'PlateLayout'), params.id])
            redirect(action: "list")
            return
        }

        redirect(action: "editAttributes", id:  plateLayoutInstance.id, params: params << [sampleProperty: "cellLine"])
    }

    def edit() {
        def plateLayoutInstance = PlateLayout.get(params.id)
        if (!plateLayoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'plateLayout.label', default: 'PlateLayout'), params.id])
            redirect(action: "list")
            return
        }

        [plateLayoutInstance: plateLayoutInstance]
    }

    def update() {
        def plateLayoutInstance = PlateLayout.get(params.id)
        if (!plateLayoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'plateLayout.label', default: 'PlateLayout'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (plateLayoutInstance.version > version) {
                plateLayoutInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'plateLayout.label', default: 'PlateLayout')] as Object[],
                          "Another user has updated this PlateLayout while you were editing")
                render(view: "edit", model: [plateLayoutInstance: plateLayoutInstance])
                return
            }
        }

        plateLayoutInstance.properties = params

        if (!plateLayoutInstance.save(flush: true)) {
            render(view: "edit", model: [plateLayoutInstance: plateLayoutInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'plateLayout.label', default: 'PlateLayout'), plateLayoutInstance.id])
        redirect(action: "show", id: plateLayoutInstance.id)
    }

    def delete() {
        def plateLayoutInstance = PlateLayout.get(params.id)
        if (!plateLayoutInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'plateLayout.label', default: 'PlateLayout'), params.id])
            redirect(action: "list")
            return
        }

        try {
            plateLayoutInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'plateLayout.label', default: 'PlateLayout'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'plateLayout.label', default: 'PlateLayout'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    /* methods for changing attributes */
    def sampleList(){
        def plateLayoutInstance = PlateLayout.get(params.id)
        def samples = plateLayoutInstance.wells.collect{it.sample}

        [samples: samples.unique()]
    }

    def editAttributes(){
        def plateLayoutInstance = PlateLayout.get(params.id)

        [plateLayout:  plateLayoutInstance, wells: plateLayoutInstance.wells, sampleProperty: params.sampleProperty]
    }

    def updateWellProperty()
    {
        def wellProp = params.wellProperty

        def plateLayout = params.plateLayout

        params.remove("action")
        params.remove("controller")
        params.remove("wellProperty")
        params.remove("plateLayout")

        if(params.size() == 0) render "Nothing to do"

        plateLayoutService.updateWellProperties(params, wellProp, plateLayout)

        progressService.setProgressBarValue("update${plateLayout}", 100)
        render "Save successful"
    }

    def createLayoutCopy() {
        def plateLayoutInstance = PlateLayout.get(params.id)

        def newPlateLayout = plateLayoutService.deepClone(plateLayoutInstance)
        newPlateLayout.name = params.name

        if(newPlateLayout.save(flush: true, failOnError: true)){
            flash.message = "Copy created successfully. Be aware: you are now working on the copy!"
            render (view:  "editAttributes", model: [plateLayout: newPlateLayout, wells: newPlateLayout.wells, sampleProperty: params.sampleProperty])
        }

        else{
            flash.message = "Could not create copy: " + newPlateLayout.errors.toString()
            render(view: "editAttributes", model: [plateLayout: plateLayoutInstance, wells: plateLayoutInstance.wells, sampleProperty: params.sampleProperty])
        }
    }

    def clearProperty() {
        def plateLayoutInstance = PlateLayout.get(params.id)
        plateLayoutInstance.wells.each{
            it."${params.sampleProperty}" = null
            if(!it.save(flush:true, failOnError: true))
                flash.message = "Could not clear sheet"
        }
        render(view: "editAttributes", model: [plateLayout: plateLayoutInstance, wells: plateLayoutInstance.wells, sampleProperty: params.sampleProperty])
    }

    def importLayout() {

    }
}
