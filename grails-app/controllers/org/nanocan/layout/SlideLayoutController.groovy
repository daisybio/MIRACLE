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
package org.nanocan.layout

import org.nanocan.rppa.scanner.Slide
import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured
import org.nanocan.project.Experiment
import org.nanocan.project.Project
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

@Secured(['ROLE_USER'])
class SlideLayoutController {

    //dependencies
    def springSecurityService
    def slideLayoutService
    def experimentService
    def progressService
	def clipboardParsingService
    def layoutImportService

    static allowedMethods = [save: "POST", update: "PUT", delete: "POST"]


    def showSpotTooltip(){

        render template: "spotPreview", model: [layoutSpotInstance: LayoutSpot.get(params.long("id"))]
    }

    def index() {

        //deal with max
        if(!params.max && session.maxSlideLayout) params.max = session.maxSlideLayout
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        session.maxSlideLayout = params.max

        //deal with offset
        params.offset = params.offset?:(session.offsetSlideLayout?:0)
        session.offsetSlideLayout = params.offset

        def slideLayoutInstanceList
        def slideLayoutInstanceListTotal

        if(session.experimentSelected)
        {
            slideLayoutInstanceList = Experiment.get(session.experimentSelected).slideLayouts
        }
        else if(session.projectSelected)
        {
            slideLayoutInstanceList = []
            Experiment.findAllByProject(Project.get(session.projectSelected as Long))?.each{slideLayoutInstanceList.addAll(it.slideLayouts)}
        }
        else
        {
            slideLayoutInstanceList = SlideLayout.list(params)
            slideLayoutInstanceListTotal = SlideLayout.count()
        }

        //create subset of list
        if(session.experimentSelected || session.projectSelected)
        {
            slideLayoutInstanceListTotal = slideLayoutInstanceList?.size()?:0

            //fix offset
            if(params.int('offset') >= slideLayoutInstanceListTotal) params.offset = 0

            if(slideLayoutInstanceListTotal > 0)
            {
                int rangeMin = Math.min(slideLayoutInstanceListTotal, params.int('offset'))
                int rangeMax = Math.min(slideLayoutInstanceListTotal, (params.int('offset') + params.int('max')))

                slideLayoutInstanceList = slideLayoutInstanceList.asList()
                if(params.sort) slideLayoutInstanceList = slideLayoutInstanceList.sort{it[params.sort]}
                else slideLayoutInstanceList.sort{ a,b -> a.id <=> b.id}
                if(params.order == "desc") slideLayoutInstanceList = slideLayoutInstanceList.reverse()

                slideLayoutInstanceList = slideLayoutInstanceList.subList(rangeMin, rangeMax)
            }
        }

        [slideLayoutInstanceList: slideLayoutInstanceList, slideLayoutInstanceTotal: slideLayoutInstanceListTotal]
    }

    def create() {
        [slideLayoutInstance: new SlideLayout(params), experiments: Experiment.list()]
    }

    def sampleSpotTable(){
        def slideLayoutInstance = SlideLayout.get(params.id)
        def spots = slideLayoutInstance.sampleSpots

        [slideLayout:  slideLayoutInstance, spots: spots, sampleProperty: params.sampleProperty]
    }
	
	def parseClipboardData(){
		println params					//params is an object with all the parameters from the view.
		def slideLayoutInstance = SlideLayout.get(params.id)
		
		def spots = clipboardParsingService.parse(params.excelPasteBin, slideLayoutInstance)
		println params.spotProperty
		def model = [sampleProperty: params.spotProperty, blocksPerRow : slideLayoutInstance.blocksPerRow,columnsPerBlock : slideLayoutInstance.columnsPerBlock, 
			rowsPerBlock : slideLayoutInstance.rowsPerBlock,spots:spots, numberOfBlocks: slideLayoutInstance.numberOfBlocks]
		render template: "tableTemplate", model: model
	}


    def copySlideLayout()
    {
        def slideLayoutInstance = SlideLayout.get(params.id)

        def selectedExperiments = experimentService.findExperiment(slideLayoutInstance)
        def experiments = Experiment.list()

        if(SlideLayout.findByTitle(params.name)){
            flash.message = "Could not create copy: Name already in use"
            render(view: "show", model: [slideLayoutInstance: slideLayoutInstance,
                                         experiments: experimentService.findExperiment(slideLayoutInstance)])
            return
        }

        SlideLayout newSlideLayout = slideLayoutService.copySlideLayout(slideLayoutInstance, params.name)
        newSlideLayout.lastUpdatedBy = springSecurityService.currentUser
        newSlideLayout.createdBy = springSecurityService.currentUser

        experiments.removeAll(selectedExperiments)

        if(newSlideLayout.save(flush: true)){
            //also add to same experiments
            selectedExperiments.each{ experimentService.addToExperiment(newSlideLayout, it) }
            flash.message = "Copy created successfully. Be aware: you are now working on the copy!"
            render (view:  "show", model: [slideLayoutInstance: newSlideLayout, experiments: experimentService.findExperiment(newSlideLayout)])
        }
        else{
            flash.message = "Could not create copy: " + newSlideLayout.errors.toString()
            render (view:  "show", model: [slideLayoutInstance: newSlideLayout, experiments: experimentService.findExperiment(newSlideLayout)])
        }
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

        experimentService.addToExperiment(slideLayoutInstance, params.experimentsSelected)

		flash.message = message(code: 'default.created.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), slideLayoutInstance.id])
        redirect(action: "show", id: slideLayoutInstance.id)
    }

    def show() {
        def slideLayoutInstance = SlideLayout.get(params.id)
        if (!slideLayoutInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), params.id])
            redirect(action: "index")
            return
        }

        [slideLayoutInstance: slideLayoutInstance, experiments: experimentService.findExperiment(slideLayoutInstance), slidesWithThisLayout: Slide.findAllByLayout(slideLayoutInstance)]
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
            redirect(action: "index")
            return
        }

        def selectedExperiments = experimentService.findExperiment(slideLayoutInstance)

        [slideLayoutInstance: slideLayoutInstance, experiments: Experiment.list(), selectedExperiments: selectedExperiments]
    }



    def update() {
        def slideLayoutInstance = SlideLayout.get(params.id)
        if (!slideLayoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), params.id])
            redirect(action: "index")
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
            render(view: "edit", model: [slideLayoutInstance: slideLayoutInstance, experiments: Experiment.list(),
                    selectedExperiments: slideLayoutService.findExperiment(slideLayoutInstance)])
            return
        }

        experimentService.updateExperiments(slideLayoutInstance, params.experimentsSelected)

		flash.message = message(code: 'default.updated.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), slideLayoutInstance.id])
        redirect(action: "show", id: slideLayoutInstance.id)
    }

    def delete() {
        def slideLayoutInstance = SlideLayout.get(params.id)
        if (!slideLayoutInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), params.id])
            redirect(action: "index")
            return
        }

/*        else if(Slide.findByLayout(slideLayoutInstance)){
            flash.message = "Cannot delete layout while there is still a slide using it."
            redirect(action: "show", id: params.id)
            return
        }*/

        try {
            experimentService.updateExperiments(slideLayoutInstance, [])

            if (slideLayoutInstance.sampleSpots.size() > 0){
                slideLayoutService.deleteLayoutSpots(slideLayoutInstance.id)
                slideLayoutInstance.discard()
                slideLayoutInstance = SlideLayout.get(params.id)
            }

            slideLayoutInstance.delete(flush:true)

			flash.message = message(code: 'default.deleted.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), params.id])
            redirect(action: "index")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'slideLayout.label', default: 'SlideLayout'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
