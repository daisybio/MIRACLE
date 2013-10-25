package org.nanocan.rppa.webflow

import org.apache.commons.collections.map.HashedMap;
import org.codehaus.groovy.grails.web.json.JSONArray;
import org.codehaus.groovy.grails.web.json.JSONObject;
import org.nanocan.rppa.layout.CellLine
import org.nanocan.rppa.layout.Dilution
import org.nanocan.rppa.layout.Inducer
import org.nanocan.rppa.layout.LysisBuffer
import org.nanocan.rppa.layout.NumberOfCellsSeeded
import org.nanocan.rppa.layout.PlateLayout
import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.rppa.layout.SpotType
import org.nanocan.rppa.layout.Treatment
import org.nanocan.rppa.project.Project
import org.nanocan.rppa.rnai.Identifier
import org.nanocan.rppa.rnai.Sample
import org.nanocan.rppa.scanner.Slide
import org.nanocan.rppa.scanner.Antibody

import grails.converters.JSON
import grails.converters.XML


class WebflowController {
	def flowPlateLayoutService
	def flowSlideLayoutService
	def idCountingService
	def IOService

	def rppaFlow = {
		init{
			action{
				//creates list of PlateLayouts in flow object, if it doesn't already exist
				flow.listOfPlateLayouts = [:]
				flow.listOfSlideLayouts = [:]
				flow.listOfSlides = [:]			//Not added marshaller
				flow.plateLayoutInstance = [:]	// new PlateLayout()
				flow.listOfCellLines = [:]
				flow.listOfDilutionFactors = [:]
				flow.listOfInducers = [:]
				flow.listOfLysisBuffers = [:]
				flow.listOfNumberOfCellsSeeded = [:]
				flow.listOfSpotTypes = [:]
				flow.listOfTreatments = [:]
				flow.listOfSamples = [:]
				flow.listOfIdentifiers = [:]
				flow.listOfAntibodys = [:]
			}
			on('success').to('overView')
		}
		overView {
			on('addPlatelayouts').to('plateLayoutModel')
			on('editPlateLayout'){
				flow.plateLayoutInstance = flow.listOfPlateLayouts.get(params.int("id"))
				flow.plateLayoutInstance.id = params.int("id")
			}.to('editAttributesModel')
			on('addSlidelayout').to('slideLayoutModel')
			on('editSlideLayout'){
				flow.slideLayoutInstance = flow.listOfSlideLayouts.get(params.int("id"))
				flow.slideLayoutInstance.id = params.int("id")
				println flow.slideLayoutInstance
			}.to('editSlideLayoutModel')
			on('addSlide').to('slideModel')
			on('addCellLine').to('cellLineModel')
			on('addDilution').to('dilutionFactorModel')
			on('addInducer').to('inducerModel')
			on('addLysisBuffer').to('lysisBufferModel')
			on('addNumberOfCellsSeeded').to('numberOfCellsSeededModel')
			on('addSpotType').to('spotTypeModel')
			on('addTreatment').to('treatmentModel')
			on('addSample').to('sampleModel')
			on('addIdentifier').to('identifierModel')
			on('addAntibody').to('antibodyModel')
			on('XMLgen'){
				println flow.listOfSlides?.values()?.asList()
				//grails.converters.xml.pretty.print(true)
				//println "flow: " + flow

				def res = IOService.exportFlowAsJSON(flow)

				IOService.parseFileAsFlow(res, flow)


				def userData = JSON.parse(res) as JSONArray
				println "userData: " + userData



				//res = "[{\"class\":\"org.nanocan.rppa.layout.CellLine\",\"id\":1,\"name\":\"ce1\",\"color\":\"#bd00bd\"},{\"class\":\"org.nanocan.rppa.layout.CellLine\",\"id\":2,\"name\":\"dse\",\"color\":\"#008be8\"},{\"class\":\"org.nanocan.rppa.layout.CellLine\",\"id\":1,\"name\":\"ce1\",\"color\":\"#bd00bd\"},{\"class\":\"org.nanocan.rppa.layout.CellLine\",\"id\":2,\"name\":\"dse\",\"color\":\"#008be8\"}]"


				//println "json obj: " + res
				//println "[:].class " + [:].class
				//println "map: " + flow.listOfPlateLayouts

				/*
				 for(Object o in userData){
				 println "Objects: " + o
				 }
				 */

				//?.values()?.toList()

				/*
				 String result = flow.listOfPlateLayouts?.values()?.toList().encodeAsJSON()
				 result += flow.listOfSlideLayouts.encodeAsJSON()
				 result += flow.listOfSlides.encodeAsJSON()
				 result += flow.listOfCellLines?.values()?.toList().encodeAsJSON()
				 result += flow.listOfDilutionFactors.encodeAsJSON()
				 result += flow.listOfInducers.encodeAsJSON()
				 result += flow.listOfLysisBuffers.encodeAsJSON()
				 result += flow.listOfNumberOfCellsSeeded.encodeAsJSON()
				 result += flow.listOfSpotTypes.encodeAsJSON()
				 result += flow.listOfTreatments.encodeAsJSON()
				 flow.listOfPlateLayouts 
				 flow.listOfSlideLayouts 
				 flow.listOfSlides
				 flow.listOfCellLines 
				 flow.listOfDilutionFactors 
				 flow.listOfInducers 
				 flow.listOfLysisBuffers 
				 flow.listOfNumberOfCellsSeeded
				 flow.listOfSpotTypes 
				 flow.listOfTreatments 
				 flow.listOfSamples
				 flow.listOfIdentifiers 
				 flow.listOfAntibodys 
				 */
			}.to('overView')
			//on('add Block Shifts').to('blockShiftState')
			//on('do analysis').to('analysisState')
			on('cancel').to('finish')
		}
		plateLayoutModel {
			action {
				[plateLayoutInstance : new PlateLayout()]
			}
			on('success').to('plateLayoutState')
		}

		plateLayoutState {
			on('save'){PlateLayout plateLayout ->
				/*if(plateLayout.hasErrors()) {
				 flash.message = "Validation error"
				 return error()
				 }*/

				//uses the idCounting service to generate unique id value for the object.
				def newId = idCountingService.getId("PlateLayout")
				plateLayout.id = newId

				//attaches the right number of rows and colums, depending on the format of the plateLayout selected
				plateLayout.beforeInsert()

				//creates weels for new platelayout
				flowPlateLayoutService.createWellLayouts(plateLayout)

				//add's the new plateLayout to list of plateLayout's in the flow object
				flow.listOfPlateLayouts.put(newId, plateLayout)

				//persists the plateLayout in the flow for the editAttributeState to know what object to work on
				flow.plateLayoutInstance = plateLayout

			}.to('overView')
			on('Continue').to('editAttributesModel')
			on('cancel').to('finish')
		}
		editAttributesModel{
			action{
				//println "well 5 = " + flow.plateLayoutInstance.wells.toList().get(5).id
				[plateLayout:flow.plateLayoutInstance,
					wells: flow.plateLayoutInstance.wells, 
					sampleProperty:params.sampleProperty?:"cellLine",
					cellLineList:flow.listOfCellLines?.values()?.toList(),
					inducerList:flow.listOfInducers?.values()?.toList(),
					treatmentList:flow.listOfTreatments?.values()?.toList(),
					numberOfCellsSeededList:flow.listOfNumberOfCellsSeeded?.values()?.toList(),
					spotTypeList:flow.listOfSpotTypes?.values()?.toList()]
			}
			on('success').to('editAttributesState')
		}
		editAttributesState{
			on('saveChanges'){PlateLayout plateLayout ->
				/*if(plateLayout.hasErrors()) {
				 flash.message = "Validation error"
				 return error()
				 }*/

				//Getting the desired PL and Lists necessary for editting attributes
				plateLayout = flow.plateLayoutInstance
				def cellLines = flow.listOfCellLines
				def inducers = flow.listOfInducers
				def spotTypes = flow.listOfSpotTypes
				def treatments = flow.listOfTreatments
				def numberOfCellsSeeded = flow.listOfNumberOfCellsSeeded
				def samples = flow.listOfSamples

				println "Entering the Matrix "
				println "pl ID: " + plateLayout.id + " name " + plateLayout


				//Assigning the desired attribute's to the desired wells
				flowPlateLayoutService.updateWellProperty(params,plateLayout,cellLines,inducers,spotTypes,treatments,numberOfCellsSeeded,samples)

			}.to('editAttributesState')
			on('changeAttribute'){
				println "Neo, i want to be free of this matrix"
			}.to('editAttributesModel')
			on('finish').to('overView')
			on('cancel').to('finish')
		}
		editPlateLayoutModel{
			action{
				[plateLayoutInstance:flow.plateLayoutInstance]
			}
			on("success").to("editPlateLayoutState")
		}
		editPlateLayoutState{
			on('save'){PlateLayout plateLayout ->
				if(plateLayout.hasErrors()) {
					flash.message = "Validation error"
					return error()
				}
				plateLayout.id = flow.plateLayoutInstance.id
				flow.listOfPlateLayouts.put((int)plateLayout.id, plateLayout)
				println "test: " + flow.listOfPlateLayouts
			}.to('overView')
			on('cancel').to('finish')
		}
		slideLayoutModel{
			action{
				[slideLayoutInstance: new SlideLayout()]
			}
			on("success").to("slideLayoutState")
		}
		slideLayoutState{
			on('save'){SlideLayout slideLayout ->
				flowSlideLayoutService.createSampleSpots(slideLayout)
				def newId = idCountingService.getId("SlideLayout")
				slideLayout.id = newId
				flow.listOfSlideLayouts.put(newId as int, slideLayout)
			}.to('overView')
			on('addSlide').to('slideState')
			on('Overview').to('overView')
			on('add Platelayouts').to('plateLayoutState')
			//on('add Spots').to('spotState')
			//on('add Block Shifts').to('blockShiftState')
			//on('do analysis').to('analysisState')
			on('cancel').to('finish')
		}
		editSlideLayoutModel{
			action{
				[slideLayoutInstance: flow.slideLayoutInstance,
					CellLineList:flow.listOfCellLines?.values()?.toList(),
					LysisBufferList:flow.listOfLysisBuffers?.values()?.toList(),
					DilutionList:flow.listOfDilutionFactors?.values()?.toList(),
					InducerList:flow.listOfInducers?.values()?.toList(),
					SpotTypeList:flow.listOfSpotTypes?.values()?.toList(),
					TreatmentList:flow.listOfTreatments?.values()?.toList(),
					NumberOfCellsSeededList:flow.listOfNumberOfCellsSeeded?.values()?.toList(),
					sampleProperty:params.sampleProperty?:'cellLine']
			}
			on("success").to("editSlideLayoutState")				//Make editSlideLayoutState editSlideLayoutState
		}
		editSlideLayoutState{
			on('save'){SlideLayout slideLayoutInstance ->
				/*
				def CellLineList = flow.listOfCellLines?.values()?.toList()
				def LysisBufferList = flow.listOfLysisBuffers?.values()?.toList()
				def DilutionList = flow.listOfDilutionFactors?.values()?.toList()
				def InducerList = flow.listOfInducers?.values()?.toList()
				def SpotTypeList = flow.listOfSpotTypes?.values()?.toList()
				def TreatmentList = flow.listOfTreatments?.values()?.toList()
				def NumberOfCellsSeededList = flow.listOfNumberOfCellsSeeded?.values()?.toList()
				*/
				/*
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
				 */

				//redirect(action: "show", id: slideLayoutInstance.id)
			}.to('overView')
			on('Overview').to('overView')
		}
		slideModel{
			action{
				[slideInstance: new Slide(),antibodyList:flow.listOfAntibodys?.values()?.asList(),slideLayoutList:flow.listOfSlideLayouts?.values()?.asList()]
			}
			on("success").to("slideState")
		}
		slideState{
			on('save'){Slide slide ->


				println "something is working"

				def slideInstance = IOService.dealWithFileUploads(request, slide)

				/*if (!slideInstance.save(flush: true, failOnError: true)) {
				 render(view: "create", model: [slideInstance: slideInstance])
				 }*/

				//flash.message = message(code: 'default.created.message', args: [message(code: 'slide.label', default: 'Slide'), slideInstance.id])

				//slide = slideInstance
				def b = new FlowExportController()
				b.exportSpotsAsJSONFile()

				//println slide.layout.sampleSpots		//nothing happens because the samplespots dont get initiated
				//println slide.spots

				def newId = idCountingService.getId("Slide")
				slide.id = newId
				flow.slideInstance = slide
				flow.listOfSlides.put(newId, slide)
			}.to('showSlideModel')
			on('Overview').to('overView')
			on('add Platelayouts').to('plateLayoutState')
			//on('add Spots').to('spotState')
			//on('add Block Shifts').to('blockShiftState')
			//on('do analysis').to('analysisState')
			on('cancel').to('finish')
		}
		showSlideModel{
			action{
				[slideInstance: flow.slideInstance]
			}
			on("success").to("showSlideState")
		}
		showSlideState{
			on('save'){Slide slide ->
			}.to('overView')
			on('addSpots'){Slide slideInstance ->
				def index = 1

				def fileEnding = FilenameUtils.getExtension(slideInstance.resultFile.filePath)

				def sheets = spotImportService.getSheets(slideInstance).collect{
					[index: index++, name: it]
				}

				[slideInstance: slideInstance, configs: ResultFileConfig.list(), fileEnding: fileEnding, sheets: sheets]
			}.to('slideLayoutAddSpotsState')
			on('addBlockShifts').to('addBlockShiftsModel')
			on('Overview').to('overView')
			on('add Platelayouts').to('plateLayoutState')
			//on('add Block Shifts').to('blockShiftState')
			//on('do analysis').to('analysisState')
			on('cancel').to('finish')
		}

		addBlockShiftsModel{
			action{
				def hblockShifts = flow.slideInstance?.blockShifts?.sort{ it.blockNumber }.collect{ it.horizontalShift }
				def vblockShifts = flow.slideInstance?.blockShifts?.sort{ it.blockNumber }.collect{ it.verticalShift }
				[slideInstance: flow.slideInstance, hblockShifts: hblockShifts, vblockShifts: vblockShifts]
			}
			on("success").to("addBlockShiftsState")
		}
		addBlockShiftsState{
			on('save'){Slide slide ->
				/*
				 def newId = idCountingService.getId("Slide")
				 slide.id = newId
				 flow.slideInstance = slide
				 flow.listOfSlides.put(newId, slide)
				 */
			}.to('overView')
			//on('add Spots').to('spotState')
			on('Overview').to('overView')
			on('add Platelayouts').to('plateLayoutState')
			//on('add Block Shifts').to('blockShiftState')
			//on('do analysis').to('analysisState')
			on('cancel').to('finish')
		}

		cellLineModel{
			action{
				[cellLineInstance : new CellLine()]
			}
			on('success').to('cellLineState')
		}
		cellLineState{
			on('save'){CellLine cellLine ->
				if(cellLine.hasErrors()){
					flash.message = "Validation error"
					return error()
				}

				def newId = idCountingService.getId("CellLine")
				cellLine.id = newId
				flow.listOfCellLines.put(newId, cellLine)
			}.to('overView')
		}
		dilutionFactorModel{
			action{
				[dilutionInstance : new Dilution()]
			}
			on("success").to("dilutionFactorState")
		}
		dilutionFactorState{
			on('save'){Dilution dilutionFactor->
				/*if(dilutionFactor.hasErrors){
				 flash.message = "Validation error"
				 return error()
				 }
				 */
				def newId = idCountingService.getId("Dilution")
				dilutionFactor.id = newId
				flow.listOfDilutionFactors.put(newId, dilutionFactor)
			}.to('overView')
		}
		inducerModel{
			action{
				[inducerInstance : new Inducer()]
			}
			on("success").to("inducerState")
		}
		inducerState{
			on('save'){Inducer inducer->
				/*if(inducer.hasErrors){
				 flash.message = "Validation error"
				 return error()
				 }*/
				def newId = idCountingService.getId("Inducer")
				inducer.id = newId
				flow.listOfInducers.put(newId, inducer)
			}.to('overView')
		}
		lysisBufferModel{
			action{
				[lysisBufferInstance: new LysisBuffer()]
			}
			on("success").to("lysisBufferState")
		}
		lysisBufferState{
			on('save'){LysisBuffer lysisBuffer->
				/*
				 if(dilutionFactor.hasErrors){
				 flash.message = "Validation error"
				 return error()
				 }
				 */
				def newId = idCountingService.getId("LysisBuffer")
				lysisBuffer.id = newId
				flow.listOfLysisBuffers.put(newId, lysisBuffer)
			}.to('overView')
		}
		numberOfCellsSeededModel{
			action{
				[numberOfCellsSeededInstance:new NumberOfCellsSeeded()]
			}
			on('success').to('numberOfCellsSeededState')
		}
		numberOfCellsSeededState{
			on('save'){NumberOfCellsSeeded numberOfCellsSeeded->
				/*if(numberOfCellsSeeded.hasErrors){
				 flash.message = "Validation error"
				 return error()
				 }*/
				def newId = idCountingService.getId("NumberOfCellsSeeded")
				numberOfCellsSeeded.id = newId
				flow.listOfNumberOfCellsSeeded.put(newId, numberOfCellsSeeded)
			}.to('overView')
		}
		spotTypeModel{
			action{
				[spotTypeInstance:new SpotType()]
			}
			on('success').to('spotTypeState')
		}
		spotTypeState{
			on('save'){SpotType spotType->
				/*if(spotType.hasErrors){
				 flash.message = "Validation error"
				 return error()
				 }*/
				def newId = idCountingService.getId("SpotType")
				spotType.id = newId
				flow.listOfSpotTypes.put(newId, spotType)
			}.to('overView')
		}
		treatmentModel{
			action{
				[treatmentInstance:new Treatment()]
			}
			on('success').to('treatmentState')
		}
		treatmentState{
			on('save'){Treatment treatment->
				/*if(dilutionFactor.hasErrors){
				 flash.message = "Validation error"
				 return error()
				 }*/
				def newId = idCountingService.getId("Treatment")
				treatment.id = newId
				flow.listOfTreatments.put(newId, treatment)
			}.to('overView')
		}

		// These are Savanah attributes Should they be taken into the webflow ? if so remember to add Identety as well
		sampleModel{
			action{
				[sampleInstance:new Sample()]
			}
			on('success').to('sampleState')
		}
		sampleState{
			on('save'){Sample sample->
				/*if(sample.hasErrors){
				 flash.message = "Validation error"
				 return error()
				 }*/
				def newId = idCountingService.getId("Sample")
				sample.id = newId
				flow.listOfSamples.put(newId, sample)
			}.to('overView')
			on('add'){
				[identifierInstance:new Identifier()]
			}.to('identifierState')
		}
		identifierModel{
			action{
				[identifierInstance:new Identifier(),sampleInstanceList:flow.listOfSamples?.values()?.asList()]
			}
			on('success').to('identifierState')
		}
		identifierState{
			on('save'){Identifier identifier->
				/*if(dilutionFactor.hasErrors){
				 flash.message = "Validation error"
				 return error()
				 }*/
				def newId = idCountingService.getId("Identifier")
				identifier.id = newId
				flow.listOfIdentifiers.put(newId, identifier)
			}.to('overView')
		}
		antibodyModel{
			action{
				[antibodyInstance:new Antibody()]
			}
			on('success').to('antibodyState')
		}
		antibodyState{
			on('save'){Antibody antibody->
				/*if(dilutionFactor.hasErrors){
				 flash.message = "Validation error"
				 return error()
				 }*/

				def newId = idCountingService.getId("Antibody")
				antibody.id = newId
				flow.listOfAntibodys.put(newId, antibody)
			}.to('overView')
		}
		finish {
			redirect(controller: 'webflow')
		}
		error {
			on('confirm').to('finish')
		}
	}

	def plateLayoutSpottingFlow = {

		modelForPlateLayouts{
			action {
				[miracleLayouts: flow.listOfPlateLayouts]
			}
			on("success").to "selectPlateLayouts"
		}

		selectPlateLayouts{
			on("plateLayoutsOrdered").to "plateLayoutsOrdered"
		}
		plateLayoutsOrdered{
			action {
				if (!params["plateLayoutOrder"])
				{
					flash.message = "No plate layouts have been selected"
					noSelection()
				}
				else
				{

					def selectedLayouts = params["plateLayoutOrder"].split("&").collect{it.split("=")[1]}
					def layouts = [:]
					//boolean hasSavanahLayouts = false

					flow.selectedLayouts = selectedLayouts

					selectedLayouts.each{
						layouts = plateImportService.getPlateLayoutFromId(it, layouts)
						layouts = layouts
					}
					flow.layouts = layouts
					spottingProperties()
				}
			}
			on("noSelection").to "modelForPlateLayouts"
			on("spottingProperties").to "spottingProperties"
		}
		spottingProperties{
			on("continue"){
				flash.nobanner = true

				def extractions = [:]
				def excludedPlateExtractionsMap = [:]
				def extractionCount = 0

				params.list("layouts").each{

					def excludedPlateExtractions = []
					for(int extraction in 1..params.int("numOfExtractions")){
						def extractionExcluded = "Plate_"+it.toString()+"|Extraction_"+extraction+"|Field"
						boolean excludeThisExtraction = params.boolean(extractionExcluded)
						excludedPlateExtractions << excludeThisExtraction
						if(!excludeThisExtraction) extractionCount++
						excludedPlateExtractionsMap.put(extractionExcluded, params.boolean(extractionExcluded))
					}
					extractions.put(it, excludedPlateExtractions)
				}
				flow.extractionCount = extractionCount
				flow.extractions = extractions
				flow.excludedPlateExtractions = excludedPlateExtractionsMap

				flow.title = params.title
				flow.xPerBlock = params.int("xPerBlock")
				flow.spottingOrientation = params.spottingOrientation
				flow.extractorOperationMode = params.extractorOperationMode
				flow.depositionPattern = params.depositionPattern
				flow.bottomLeftDilution = params.bottomLeftDilution
				flow.topLeftDilution = params.topLeftDilution
				flow.topRightDilution = params.topRightDilution
				flow.bottomRightDilution = params.bottomRightDilution
				if(params.defaultLysisBuffer) flow.defaultLysisBuffer = LysisBuffer.get(params.defaultLysisBuffer)
				if(params.defaultSpotType) flow.defaultSpotType = SpotType.get(params.defaultSpotType)

				if(!(params.depositionPattern ==~ /\[([1-9],)+[1-9]\]/))
				{
					progressService.setProgressBarValue(params.progressId, 100)
					flash.message = "The deposition pattern is invalid!"
					error()
				}

				if(!params.title || params.title == "")
				{
					progressService.setProgressBarValue(params.progressId, 100)
					flash.message = "You have to give a title to this layout!"
					error()
				}
				//check if title is taken
				else if(SlideLayout.findByTitle(params.title))
				{
					progressService.setProgressBarValue(params.progressId, 100)
					flash.message = "Please select another title (this one already exists)."
					error()
				}
				else
				{
					flow.progressId = params.progressId
					success()
				}
			}.to "spotPlateLayouts"
		}

		spotPlateLayouts{
			action {

				def slideLayout
				try{
					slideLayout = plateImportService.importPlates(flow)
				} catch(Exception e)
				{
					flash.message = "Import failed with exception: " + e.getMessage()
					return spottingProperties()
				}

				//slideLayout.lastUpdatedBy = springSecurityService.currentUser
				//slideLayout.createdBy = springSecurityService.currentUser

				if (slideLayout.save(flush: true, failOnError: true)) {
					progressService.setProgressBarValue(flow.progressId, 100)
					flow.layoutId = slideLayout.id
					flash.message = "Slide Layout successfully created"
					success()
				}
				else{
					flash.message = "import succeeded, but persisting the slide layout failed: " + slideLayout.errors.toString()
					spottingProperties()
				}
			}
			on("spottingProperties").to "spottingProperties"
			on("success").to "showLayout"
		}

		showLayout{
			redirect(controller: "slideLayout", action: "show", id: flow.layoutId, params: [nobanner: true])
		}
	}
}
