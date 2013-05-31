package org.nanocan.rppa.project

import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.rppa.scanner.Slide
import org.nanocan.rppa.layout.PlateLayout


class WebflowController {

	def rppaFlow = {

		overView {
			on('addPlatelayouts').to('plateLayoutModel')
			on('editPlateLayout'){flow.selectedPlateLayout = params.id}.to('editPlateLayoutModel')
			on('addSlidelayout').to('slideLayoutModel')
			on('addSlide').to('slideModel')
			on('add Spots').to('spotModel')
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
				if(!flow.listOfPlateLayouts)
					flow.listOfPlateLayouts = [:]
				flow.listOfPlateLayouts.put(plateLayout.id, plateLayout)
			}.to('overView')
			on('addSlidelayout').to('slideLayoutState')
			on('Overview').to('overView')
			on('addSlide').to('slideModel')
			//on('add Spots').to('spotState')
			//on('add Block Shifts').to('blockShiftState')
			//on('do analysis').to('analysisState')
			on('cancel').to('finish')
		}
		editPlateLayoutModel{
			action{
				if (!params)
				{
					flash.message = "No plate layouts have been selected"
					noSelection()
				}
				else
				
				println "plateLayout:" + flow.selectedPlateLayout.toString()
				[plateLayoutInstance:PlateLayout.get[flow.selectedPlateLayout.id]]
			}
			on("success").to("editPlateLayoutState")
		}
		editPlateLayoutState{
			on('save'){PlateLayout plateLayout ->
				if(plateLayout.hasErrors()) {
					flash.message = "Validation error"
					return error()
				}
				
				flow.listOfPlateLayouts.remove(plateLayout.id)
				flow.listOfPlateLayouts.put(plateLayout.id, plateLayout)
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
				if(!flow.listOfSlideLayouts)
				flow.listOfSlideLayouts = new ArrayList<SlideLayout>()
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
				if(!flow.listOfSlides)
				flow.listOfSlides = new ArrayList<Slide>()
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
		spotModel{

		}
		/*
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
		cellLineModel{
			action{
				[]
			}
			on("success").to("cellLineState")
		}
		cellLineState{

		}
		dilutionFactorsModel{
			action{
				[]
			}
			on("success").to("dilutionFactorsState")
		}
		dilutionFactorsState{

		}
		inducersModel{
			action{
				[]
			}
			on("success").to("cellLineState")
		}
		inducersState{

		}
		lysisBuffersModel{
			action{
				[]
			}
			on("success").to("cellLineState")
		}
		lysisBuffersState{

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
				[//experiments: flow.Experiment.list(), savanahProjects: flow.Project.list(),miracleProjects: org.nanocan.rppa.project.Project.list(), savanahLayouts: PlateLayout.list(),
					miracleLayouts: flow.listOfPlateLayouts]
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
