package org.nanocan.layout

import org.nanocan.plates.Plate
import org.nanocan.project.Experiment
import org.nanocan.project.Project
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_USER'])
class SpottingController {

    def springSecurityService
    def progressService
    def virtualSpottingService

    def index() {
        redirect(action: "plateSpotting")
    }

    def plateSpottingFlow = {

        modelForPlates{
            action {

                def layouts

                if(session.experimentSelected)
                {
                    layouts = Experiment.get(session.experimentSelected as Long).plates
                }
                else if(session.projectSelected)
                {
                    def experiments = Experiment.findAllByProject(Project.get(session.projectSelected as Long))
                    layouts = []
                    experiments.each{
                        layouts.addAll(it.plates)
                    }
                }
                else
                {
                    layouts = Plate.list()
                }

                def controlPlates = Plate.findAllByControlPlate(true)
                if(controlPlates) layouts.removeAll(controlPlates)

                [layouts: layouts, controlPlates: controlPlates]
            }
            on("success").to "selectPlates"
        }

        selectPlates{
            on("refresh").to "modelForPlates"
            on("platesOrdered").to "platesOrdered"
        }

        platesOrdered{
            action {
                if (!params["plateOrder"])
                {
                    flash.message = "No plate layouts have been selected"
                    noSelection()
                }
                else
                {
                    def selectedLayouts = params["plateOrder"].split("&").collect{it.split("=")[1]}
                    def layouts = [:]

                    flow.selectedLayouts = selectedLayouts

                    selectedLayouts.each{
                        def layout = Plate.get(it.toString().substring(7))
                        layouts.put(it, layout)
                    }
                    flow.layouts = layouts
                    spottingProperties()
                }
            }
            on("noSelection").to "modelForPlates"
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
                flow.extractionHead = params.extractionHead
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
            }.to "spotPlates"
        }

        spotPlates{
            action {
                     println "here"
                def slideLayout
                //try{
                    slideLayout = virtualSpottingService.importPlates(flow)
                /*} catch(Exception e)
                {
                    flash.message = "Import failed with exception: " + e.getMessage()
                    return spottingProperties()
                } */

                slideLayout.lastUpdatedBy = springSecurityService.currentUser
                slideLayout.createdBy = springSecurityService.currentUser
                flow.selectedLayouts.each{
                    slideLayout.addToSourcePlates(flow.layouts[it])
                }

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
            on("success").to "finish"
        }

        finish()
    }
}
