package org.nanocan.rppa.webflow

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
import org.nanocan.rppa.scanner.Slide
import org.nanocan.rppa.scanner.Antibody
import org.nanocan.savanah.attributes.Sample


class WebflowController {
	def flowPlateLayoutService
	def idCountingService

	def rppaFlow = {
		init{
			action{
				//creates list of PlateLayouts in flow object, if it doesn't already exist
				flow.listOfPlateLayouts = [:]
				flow.listOfSlideLayouts = new ArrayList<SlideLayout>()
				flow.listOfSlides = new ArrayList<Slide>()
				flow.plateLayoutInstance = new PlateLayout()
				flow.listOfCellLines = [:]
				flow.listOfDilutionFactors = [:]
				flow.listOfInducers = [:]
				flow.listOfLysisBuffers = [:]
				flow.listOfNumberOfCellsSeeded = [:]
				flow.listOfSpotTypes = [:]
				flow.listOfTreatments = [:]
				flow.listOfSamples = [:]
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
			on('addSlide').to('slideModel')
			on('addCellLine').to('cellLineModel')
			on('addDilution').to('dilutionFactorModel')
			on('addInducer').to('inducerModel')
			on('addLysisBuffer').to('lysisBufferModel')
			on('addNumberOfCellsSeeded').to('numberOfCellsSeededModel')
			on('addSpotType').to('spotTypeModel')
			on('addTreatment').to('treatmentModel')
			on('addSample').to('sampleModel')
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
				if(plateLayout.hasErrors()) {
					flash.message = "Validation error"
					return error()
				}

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
				[plateLayout:flow.plateLayoutInstance,wells: flow.plateLayoutInstance.wells, sampleProperty:"cellLine"]

			}
			on('success').to('editAttributesState')
		}
		editAttributesState{
			on('saveChanges'){PlateLayout plateLayout ->
				/*if(plateLayout.hasErrors()) {
				 flash.message = "Validation error"
				 return error()
				 }*/
				println params
				//platelayout = flow.listOfPlatelayouts.get(platelayout)
				println "Entering the Matrix"
				//println "pl ID: " + params.plateLayout.id + " name " + params.plateLayout + "\n\r Params: "
				//println "fplI ID: " + flow.plateLayoutInstance.id + " name " + flow.plateLayoutInstance

				flowPlateLayoutService.updateWellProperty(params) //params, plateLayout
				//flow.plateLayoutInstance = plateLayout
				//flow.plateLayoutInstance.id = plateLayout.id
			}.to('editAttributesState')
			on('success').to('overView')
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
				[slideLayoutInstance: new SlideLayout(), projects: Project?.list()]
			}
			on("success").to("slideLayoutState")
		}
		slideLayoutState{
			on('save'){SlideLayout slideLayout ->
				if(slideLayout.hasErrors()){
					flash.message = "Validation error"
					return error()
				}

				flow.listOfSlideLayouts.add(slideLayout)
			}.to('overView')
			on('addSlide').to('slideState')
			on('Overview').to('overView')
			on('add Platelayouts').to('plateLayoutState')
			//on('add Spots').to('spotState')
			//on('add Block Shifts').to('blockShiftState')
			//on('do analysis').to('analysisState')
			on('cancel').to('finish')
		}
		slideModel{
			action{
				[slideInstance: new Slide(), projects: Project?.list()]
			}
			on("success").to("slideState")
		}
		slideState{
			on('save'){Slide slide ->

				def listOfSlides = flow.listOfSlides
				listOfSlides.add(slide)
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
				if(dilutionFactor.hasErrors){
					flash.message = "Validation error"
					return error()
				}
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
				if(dilutionFactor.hasErrors){
					flash.message = "Validation error"
					return error()
				}
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
				if(dilutionFactor.hasErrors){
					flash.message = "Validation error"
					return error()
				}
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
				if(dilutionFactor.hasErrors){
					flash.message = "Validation error"
					return error()
				}
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
				if(dilutionFactor.hasErrors){
					flash.message = "Validation error"
					return error()
				}
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
				/*if(dilutionFactor.hasErrors){
				 flash.message = "Validation error"
				 return error()
				 }*/
				def newId = idCountingService.getId("Sample")
				sample.id = newId
				flow.listOfSamples.put(newId, sample)
			}.to('overView')
		}
		/*
		 AntibodyModel{
		 action{
		 [sampleInstance:new Sample()]
		 }
		 on('success').to('sampleState')
		 }
		 AntiBodyState{
		 on('save'){Sample sample->
		 if(dilutionFactor.hasErrors){
		 flash.message = "Validation error"
		 return error()
		 }
		 def newId = idCountingService.getId("Sample")
		 sample.id = newId
		 flow.listOfSamples.put(newId, sample)
		 }.to('overView')
		 }
		 
		 
		 spotState{
		 on('add more Spots'){Spot spot ->
		 if(command.hasErrors()) {
		 flash.message = "Validation error"
		 return error()
		 }
		 if(!rppaFlow.listOfSpots)
		 rppaFlow.listOfSpots = new ArrayList<Spot>()
		 def listOfSpots = flow.listOfSlides
		 listOfSlides.add(spot)
		 }.to('spotState')
		 on('add Block Shifts').to('blockShiftState')
		 on('Overview').to('overView')
		 on('add Platelayouts').to('plateLayoutState')
		 on('add Slides').to('slideState')
		 on('do analysis').to('analysisState')
		 on('cancel').to('finish')
		 on(Exception).to('error')
		 }
		 blockShiftState{
		 on('add more Block Shifts'){BlockShift blockShift ->
		 if(command.hasErrors()) {
		 flash.message = "Validation error"
		 return error()
		 }
		 if(!rppaFlow.listOfBlockShifts)
		 rppaFlow.listOfBlockShifts = new ArrayList<BlockShift>()
		 def listOfBlockShifts = flow.listOfBlockShifts
		 blockShift.add(blockShift)
		 [:]
		 }.to('blockShiftState')
		 on('do analysis').to('analysisState')
		 on('Overview').to('overView')
		 on('add Platelayouts').to('plateLayoutState')
		 on('add Slides').to('slideState')
		 on('add Spots').to('spotState')
		 on('cancel').to('finish')
		 on(Exception).to('error')
		 }
		 analysisState{
		 on('more analysis').to('analysisState')
		 on('finish').to('complete')
		 on('Overview').to('overView')
		 on('add Platelayouts').to('plateLayoutState')
		 on('add Slides').to('slideState')
		 on('add Spots').to('spotState')
		 on('add Block Shifts').to('blockShiftState')
		 on('cancel').to('finish')
		 on(Exception).to('error')
		 }
		 complete {
		 on('save local copy of work for later resumption').to('saveState')
		 on('cancel').to('finish')
		 on(Exception).to('error')
		 }
		 saveState{
		 on('cancel').to('finish')
		 on(Exception).to('error')
		 }	*/

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
