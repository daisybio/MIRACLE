package org.nanocan.rppa.layout

import org.springframework.dao.DataIntegrityViolationException
import org.nanocan.rppa.scanner.Spot
import grails.plugins.springsecurity.Secured
import org.nanocan.rppa.project.Project
import org.nanocan.rppa.scanner.Slide

@Secured(['ROLE_USER'])
class SlideLayoutController {

    //dependencies
    def springSecurityService
    def slideLayoutService
    def projectService
    def progressService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def showSpotTooltip(){
        render template: "spotPreview", model: [layoutSpotInstance: LayoutSpot.get(params.long("id"))]
    }

    def list() {
        //deal with max
        if(!params.max && session.maxSlideLayout) params.max = session.maxSlideLayout
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        session.maxSlideLayout = params.max

        //deal with offset
        params.offset = params.offset?:(session.offsetSlideLayout?:0)
        session.offsetSlideLayout = params.offset

        def slideLayoutInstanceList
        def slideLayoutInstanceListTotal

        if(session.projectSelected)
        {
            slideLayoutInstanceList = Project.get(session.projectSelected as Long).layouts
            slideLayoutInstanceListTotal = slideLayoutInstanceList.size()

            int rangeMin = Math.min(slideLayoutInstanceListTotal, params.int('offset'))
            int rangeMax = Math.min(slideLayoutInstanceListTotal, (params.int('offset') + params.int('max')))

            slideLayoutInstanceList = slideLayoutInstanceList.asList().subList(rangeMin, rangeMax)
        }
        else
        {
            slideLayoutInstanceList = SlideLayout.list(params)
            slideLayoutInstanceListTotal = SlideLayout.count()
        }

        [slideLayoutInstanceList: slideLayoutInstanceList, slideLayoutInstanceTotal: slideLayoutInstanceListTotal]
    }

    def create() {
        [slideLayoutInstance: new SlideLayout(params), projects: Project.list()]
    }

    def sampleSpotTable(){
        def slideLayoutInstance = SlideLayout.get(params.id)

        [slideLayout:  slideLayoutInstance, spots: slideLayoutInstance.sampleSpots, sampleProperty: params.sampleProperty]
    }

    def updateSpotProperty()
    {
        def spotProp = params.spotProperty
        def className

        //dilution != dilutionFactor
        if (spotProp == "dilutionFactor") className = "dilution"
        else className = spotProp

        def slideLayout = params.slideLayout

        params.remove("action")
        params.remove("controller")
        params.remove("spotProperty")
        params.remove("slideLayout")
        params.remove("selectionMode")

        if(params.size() == 0) render "Nothing to do"

        slideLayoutService.updateSpotProperties(params, spotProp, slideLayout, className)

        progressService.setProgressBarValue("update${slideLayout}", 100)
        render "Save successful"
    }

    def save() {

        params.createdBy = springSecurityService.currentUser
        params.lastUpdatedBy = springSecurityService.currentUser

        def slideLayoutInstance = new SlideLayout(params)

        if (!slideLayoutInstance.save(flush: true)) {
            render(view: "create", model: [slideLayoutInstance: slideLayoutInstance])
            return
        }
        slideLayoutService.createSampleSpots(slideLayoutInstance)

        projectService.addToProject(slideLayoutInstance, params.projectsSelected)

		flash.message = message(code: 'default.created.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), slideLayoutInstance.id])
        redirect(action: "show", id: slideLayoutInstance.id)
    }

    def show() {
        def slideLayoutInstance = SlideLayout.get(params.id)
        if (!slideLayoutInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), params.id])
            redirect(action: "list")
            return
        }

        [slideLayoutInstance: slideLayoutInstance, projects: projectService.findProject(slideLayoutInstance)]
    }

    def sampleList(){
        def slideLayoutInstance = SlideLayout.get(params.id)

        def samples = slideLayoutInstance.sampleSpots.collect{
            it.sample
        }

        [samples: samples.unique()]
    }

    def edit() {
        def slideLayoutInstance = SlideLayout.get(params.id)
        if (!slideLayoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), params.id])
            redirect(action: "list")
            return
        }

        def projects = projectService.findProject(slideLayoutInstance)

        [slideLayoutInstance: slideLayoutInstance, projects: Project.list(), selectedProjects: projects]
    }



    def update() {
        def slideLayoutInstance = SlideLayout.get(params.id)
        if (!slideLayoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (slideLayoutInstance.version > version) {
                slideLayoutInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'slideLayout.label', default: 'SlideLayout')] as Object[],
                          "Another user has updated this SlideLayout while you were editing")
                render(view: "edit", model: [slideLayoutInstance: slideLayoutInstance])
                return
            }
        }

        params.lastUpdatedBy = springSecurityService.currentUser

        slideLayoutInstance.properties = params

        if (!slideLayoutInstance.save(flush: true)) {
            render(view: "edit", model: [slideLayoutInstance: slideLayoutInstance, projects: Project.list(),
                    selectedProjects: slideLayoutService.findProject(slideLayoutInstance)])
            return
        }

        projectService.updateProjects(slideLayoutInstance, params.projectsSelected)

		flash.message = message(code: 'default.updated.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), slideLayoutInstance.id])
        redirect(action: "show", id: slideLayoutInstance.id)
    }

    def delete() {
        def slideLayoutInstance = SlideLayout.get(params.id)
        if (!slideLayoutInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), params.id])
            redirect(action: "list")
            return
        }

        else if(Slide.findByLayout(slideLayoutInstance)){
            flash.message = "Cannot delete layout while there is still a slide using it."
            redirect(action: "show", id: params.id)
            return
        }

        try {
            projectService.updateProjects(slideLayoutInstance, [])
            slideLayoutInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
