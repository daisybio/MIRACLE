package org.nanocan.rppa.layout

import org.springframework.dao.DataIntegrityViolationException
import org.nanocan.rppa.scanner.Spot
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class SlideLayoutController {

    //dependencies
    def springSecurityService
    def slideLayoutService

    static navigation = [
            group: 'main',
            title: 'Slide Layout'
    ]

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [slideLayoutInstanceList: SlideLayout.list(params), slideLayoutInstanceTotal: SlideLayout.count()]
    }

    def create() {
        [slideLayoutInstance: new SlideLayout(params)]
    }

    def sampleSpotTable(){
        def slideLayoutInstance = SlideLayout.get(params.id)

        [slideLayout:  slideLayoutInstance, spots: slideLayoutInstance.sampleSpots, sampleProperty: params.sampleProperty]
    }

    def progressService

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

        [slideLayoutInstance: slideLayoutInstance]
    }

    def edit() {
        def slideLayoutInstance = SlideLayout.get(params.id)
        if (!slideLayoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), params.id])
            redirect(action: "list")
            return
        }

        [slideLayoutInstance: slideLayoutInstance]
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
            render(view: "edit", model: [slideLayoutInstance: slideLayoutInstance])
            return
        }

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

        try {
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
