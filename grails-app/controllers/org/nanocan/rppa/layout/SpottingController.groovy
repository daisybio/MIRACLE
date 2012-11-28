package org.nanocan.rppa.layout

import org.springframework.security.access.annotation.Secured
import org.nanocan.savanah.experiment.Experiment
import org.nanocan.savanah.plates.PlateLayout
import org.nanocan.savanah.experiment.Project

@Secured(['ROLE_USER'])
class SpottingController {

    def plateImportService
    def springSecurityService
    def progressService

    def index() {
        redirect(action: "plateLayoutSpotting")
    }

    def plateLayoutSpottingFlow = {

        modelForPlateLayouts{
            action {
                [experiments: Experiment.list(), savanahProjects: Project.list(),
                        miracleProjects: org.nanocan.rppa.project.Project.list(),
                        savanahLayouts: PlateLayout.list(),
                        miracleLayouts: org.nanocan.rppa.layout.PlateLayout.list()]
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
                    boolean hasSavanahLayouts = false

                    flow.selectedLayouts = selectedLayouts

                    selectedLayouts.each{
                        if(it.toString().startsWith("savanah")) hasSavanahLayouts = true
                        layouts = plateImportService.getPlateLayoutFromId(it, layouts)
                        layouts = layouts
                    }
                    flow.layouts = layouts
                    if(hasSavanahLayouts) matchSavanahProperties()
                    else spottingProperties()
                }
            }
            on("noSelection").to "modelForPlateLayouts"
            on("matchSavanahProperties").to "modelForMatchSavanahProperties"
            on("spottingProperties").to "spottingProperties"
        }

        modelForMatchSavanahProperties{
            action {
                List<org.nanocan.savanah.plates.PlateLayout> savanahLayouts = new ArrayList<org.nanocan.savanah.plates.PlateLayout>()
                flow.layouts.values().each {
                    if(it instanceof PlateLayout) savanahLayouts << it
                }
                def matchModel = plateImportService.createMatchListsForSavanahLayouts(savanahLayouts, false)
                matchModel
            }
            on("success").to "matchSavanahProperties"
        }

        matchSavanahProperties{
            on("continue").to "createSavanahMatchingMaps"
        }

        createSavanahMatchingMaps{
            action {
                def numberOfCellsSeededMap = [:]
                def cellLineMap = [:]
                def inducerMap = [:]
                def treatmentMap = [:]
                def sampleMap = [:]

                params.each{k,v ->
                    def indexOfSeparator = k.toString().indexOf('_') + 1

                    if(k.toString().startsWith("numberOfCellsSeeded")) numberOfCellsSeededMap.put(k.toString().substring(indexOfSeparator), NumberOfCellsSeeded.findByName(v))
                    else if(k.toString().startsWith("cellline")) cellLineMap.put(k.toString().substring(indexOfSeparator), CellLine.findByName(v))
                    else if(k.toString().startsWith("inducer")) inducerMap.put(k.toString().substring(indexOfSeparator), Inducer.findByName(v))
                    else if(k.toString().startsWith("treatment")) treatmentMap.put(k.toString().substring(indexOfSeparator), Treatment.findByName(v))
                    else if(k.toString().startsWith("sample")) sampleMap.put(k.toString().substring(indexOfSeparator), org.nanocan.rppa.rnai.Sample.findByName(v))
                }

                flow.numberOfCellsSeededMap = numberOfCellsSeededMap
                flow.cellLineMap = cellLineMap
                flow.inducerMap = inducerMap
                flow.treatmentMap = treatmentMap
                flow.sampleMap = sampleMap
            }
            on("success").to "spottingProperties"
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

                    slideLayout.lastUpdatedBy = springSecurityService.currentUser
                    slideLayout.createdBy = springSecurityService.currentUser

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
