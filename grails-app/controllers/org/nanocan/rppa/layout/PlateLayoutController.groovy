package org.nanocan.rppa.layout

import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException
import org.nanocan.rppa.project.Project
import org.nanocan.rppa.project.Experiment

@Secured(['ROLE_USER'])
class PlateLayoutController {

    def plateLayoutService
    def progressService
    def experimentService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def updateControlPlate(){
        def plateLayoutInstance = PlateLayout.get(params.id)
        def isControlPlate = false

        if (params.controlPlate == "on") isControlPlate = true

        plateLayoutInstance.controlPlate = isControlPlate

        plateLayoutInstance.save(flush:true)
        render "OK"
    }

    def list() {
        //deal with max
        if(!params.max && session.maxPlateLayout) params.max = session.maxPlateLayout
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        session.maxPlateLayout = params.max

        //deal with offset
        params.offset = params.offset?:(session.offsetPlateLayout?:0)
        session.offsetPlateLayout = params.offset

        def plateLayoutInstanceList
        def plateLayoutInstanceListTotal

        if (session.experimentSelected)
        {
            plateLayoutInstanceList = Experiment.get(session.experimentSelected).plateLayouts
        }
        else if(session.projectSelected)
        {
            plateLayoutInstanceList = Experiment.findByProject(Project.get(session.projectSelected)).plateLayouts
        }
        else
        {
            plateLayoutInstanceList = PlateLayout.list(params)
            plateLayoutInstanceListTotal = PlateLayout.count()
        }

        //fix offset when necessary
        if(params.int('offset') >= plateLayoutInstanceListTotal) params.offset = 0

        //create subset of list
        if (session.experimentSelected || session.projectSelected)
        {
            plateLayoutInstanceListTotal = plateLayoutInstanceList?.size()?:0

            if(plateLayoutInstanceListTotal > 0)
            {
                int rangeMin = Math.min(plateLayoutInstanceListTotal, params.int('offset'))
                int rangeMax = Math.min(plateLayoutInstanceListTotal, (params.int('offset') + params.int('max')))

                plateLayoutInstanceList = plateLayoutInstanceList.asList().subList(rangeMin, rangeMax)
            }
        }

        [plateLayoutInstanceList: plateLayoutInstanceList, plateLayoutInstanceTotal: plateLayoutInstanceListTotal]
    }

    def create() {
        [plateLayoutInstance: new PlateLayout(params), experiments: Experiment.list()]
    }

    def save() {
        def plateLayoutInstance = new PlateLayout(params)
        if (!plateLayoutInstance.save(flush: true)) {
            render(view: "create", model: [plateLayoutInstance: plateLayoutInstance])
            return
        }

        plateLayoutService.createWellLayouts(plateLayoutInstance)
        experimentService.addToExperiment(plateLayoutInstance, params.experimentsSelected)

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

        def experiments = experimentService.findExperiment(plateLayoutInstance)

        [plateLayoutInstance: plateLayoutInstance, experiments: Experiment.list(), selectedExperiments: experiments]
    }

    def addToExperiment(){
        experimentService.addToExperiment(PlateLayout.get(params.id), [params.long("experiment")])
        redirect(action: "editAttributes", id: params.id)
    }

    def removeFromExperiment(){
        experimentService.removeFromExperiment(PlateLayout.get(params.id), [params.long("experiment")])
        redirect(action: "editAttributes", id: params.id)
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

        experimentService.updateExperiments(plateLayoutInstance, params.experimentsSelected)

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
            experimentService.updateExperiments(plateLayoutInstance, [])
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
        def selectedExperiments = experimentService.findExperiment(plateLayoutInstance)
        def experiments = Experiment.list()
        experiments.removeAll(selectedExperiments)

        [plateLayout:  plateLayoutInstance, wells: plateLayoutInstance.wells, experiments: experiments, selectedExperiments: selectedExperiments, sampleProperty: params.sampleProperty?:"cellLine"]
    }

    def showAttributes(){
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
