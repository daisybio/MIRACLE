package org.nanocan.savanah

import org.nanocan.savanah.experiment.Experiment
import org.nanocan.savanah.plates.Plate
import org.springframework.security.access.annotation.Secured
import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.rppa.layout.NumberOfCellsSeeded
import org.nanocan.rppa.layout.CellLine
import org.nanocan.rppa.layout.Inducer
import org.nanocan.rppa.layout.Treatment
import org.nanocan.rppa.rnai.Sample
import org.nanocan.savanah.experiment.Project
import org.nanocan.savanah.plates.PlateLayout

@Secured(['ROLE_USER'])
class PlateImportController {

    def plateImportService
    def springSecurityService

    def plateLayoutImport(){
        def plateLayouts

        if(params.experiment){
            def experiment  = Experiment.get(params.experiment)
            plateLayouts = experiment.plates
        }

        else plateLayouts = PlateLayout.list()

        [experiments: Experiment.list(), projects: Project.list(), plateLayouts: plateLayouts]
    }

    def filterPlateLayoutsByExperiment(){
        def experiments = Project.findByName(params.project)
        if(!experiments) experiments = Experiment.list()
        else experiments = experiments.experiments
        render template: "experimentSelect", model: ["filterAction": "filterPlateLayouts", "experiments": experiments]
    }

    def showAttributes(){
        def plateLayoutInstance = PlateLayout.get(params.id)

        render view: "/plateLayout/showAttributes", model: [plateLayout:  plateLayoutInstance, wells: plateLayoutInstance.wells, sampleProperty: params.sampleProperty]
    }

    def sampleList(){
        def plateLayoutInstance = PlateLayout.get(params.id)
        def samples = plateLayoutInstance.wells.collect{it.sample}

        [samples: samples.unique()]
    }

    def filterPlatesByExperiment(){
        def experiments = Project.findByName(params.project)
        if(!experiments) experiments = Experiment.list()
        else experiments = experiments.experiments
        render template: "experimentSelect", model: ["filterAction": "filterPlates", "experiments": experiments]
    }

    def filterPlateLayouts(){
        def layouts = Experiment.findByName(params.experiment)
        if(!layouts) layouts = PlateLayout.list()
        else layouts = layouts.plateLayouts

        render g.select(name: "platesSelected", optionKey: "id", optionValue: "name", multiple: true, size: 20,
                from: layouts)
    }

    def filterSavanahExperimentsByProject(){
        def experiments = Project.findByName(params.project)
        if(!experiments) experiments = Experiment.list()
        else experiments = experiments.experiments

        render template: "experimentSelect", model: ["updateDiv": "savanahPlateLayoutsDiv","filterAction": "filterSavanahPlateLayoutsByExperiment", "experiments": experiments]
    }

    def filterSavanahPlateLayoutsByExperiment(){
        def layouts = Experiment.findByName(params.experiment)

        if(!layouts) layouts = PlateLayout.list()
        else layouts = layouts.plateLayouts

        render template: "plateLayoutList", model: ["layouts": layouts, "ulid": "unselectedSavanahPlateLayouts", "prefix":"layout_savanah"]
    }

    def filterMiraclePlateLayoutsByProject(){
        def layouts = org.nanocan.rppa.project.Project.findByProjectTitle(params.project)

        if(!layouts) layouts = org.nanocan.rppa.layout.PlateLayout.list()
        else layouts = layouts.plateLayouts

        render template: "plateLayoutList", model: ["layouts": layouts, "ulid": "unselectedMiraclePlateLayouts", "prefix":"layout_miracle"]
    }

    def convertAndSavePlateLayout(){
        def plateLayouts = params.list("plateLayouts")
        def miracleNewLayout

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
            else if(k.toString().startsWith("sample")) sampleMap.put(k.toString().substring(indexOfSeparator), Sample.findByName(v))
        }

        //check plate names
        flash.message = "PlateLayout(s) with name(s) "
        boolean nameConflict = false
        plateLayouts.each{ layout ->
            if(org.nanocan.rppa.layout.PlateLayout.findByName(params."${layout}_tf") != null){
                flash.message += params."${layout}_tf" + " "
                nameConflict = true
            }
        }
        if (nameConflict)
        {
            flash.message += "exists already. Choose another name"
            params.platesSelected = plateLayouts.collect{PlateLayout.findByName(it).id}
            def newModel = [:]
            newModel.putAll(params)
            newModel.putAll(importSelectedPlateLayouts())
            println newModel
            render(view: "importSelectedPlateLayouts", model: newModel)
            return
        }

        plateLayouts.each{ layout ->
            miracleNewLayout = plateImportService.importPlateLayout(layout, params."${layout}_tf", cellLineMap, numberOfCellsSeededMap, inducerMap, treatmentMap, sampleMap)
        }

        flash.message = "The following plate layouts have been imported: ${plateLayouts} - ${plateLayouts.collect{params."${it}_tf"}}"

        if(plateLayouts.size() > 1)
            redirect(controller: "plateLayout", action:"list")
        else redirect(controller: "plateLayout", action: "show", id: miracleNewLayout.id)

    }

    def importSelectedPlateLayouts(){

        def plateLayouts = params.list("platesSelected").collect{ PlateLayout.get(it)}
        plateImportService.createMatchListsForSavanahLayouts(plateLayouts)
    }

    def plateImport(){
        def plateLayouts
        def plates

        if(params.experiment){
            def experiment  = Experiment.get(params.experiment)
            plateLayouts = experiment.plateLayouts
            plates = experiment.plates
        }

        else{
            plateLayouts = PlateLayout.list()
            plates = Plate.list()
        }

        [experiments: Experiment.list(), savanahProjects: Project.list(), miracleProjects: org.nanocan.rppa.project.Project.list(), savanahPlates: plates, savanahLayouts: plateLayouts,
                miracleLayouts: org.nanocan.rppa.layout.PlateLayout.list()]
    }

    def plateLayoutsOrdered(){

        if (!params.list("layout[]"))
        {
            flash.message = "No plate layouts have been selected"
            redirect action: "plateImport"
        }
        else{
            def layouts = [:]
            boolean hasSavanahLayouts = false

            params.list("layout[]").each{
                if(it.toString().startsWith("savanah")) hasSavanahLayouts = true
                layouts = plateImportService.getPlateLayoutFromId(it, layouts)
            }
            if(hasSavanahLayouts){
                def savanahLayouts = []
                layouts.values().each {
                    if(it instanceof PlateLayout) savanahLayouts << it
                }

                def matchModel = plateImportService.createMatchListsForSavanahLayouts(savanahLayouts)
                matchModel.put("nobanner", true)
                matchModel.put("onthefly", true)
                matchModel.put("layouts", layouts.keySet())
                render( view: "importSelectedPlateLayouts", model: matchModel)
            }

            else render (view: "spotterSettings", model: [nobanner: true, layouts:  layouts])
        }
    }

    def spotterSettings(){

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
            else if(k.toString().startsWith("sample")) sampleMap.put(k.toString().substring(indexOfSeparator), Sample.findByName(v))
        }

        flash.numberOfCellsSeededMap = numberOfCellsSeededMap
        flash.cellLineMap = cellLineMap
        flash.inducerMap = inducerMap
        flash.treatmentMap = treatmentMap
        flash.sampleMap = sampleMap

        def layouts = [:]
        params.list("layouts").each{
            layouts = plateImportService.getPlateLayoutFromId(it, layouts)
        }

        [layouts: layouts]
    }

    def processPlateLayouts(){

        println params
        println flash

        def plateLayouts = params.list("layouts")
        def layouts = [:]

        plateLayouts.each{
            layouts = plateImportService.getPlateLayoutFromId(it, layouts)
        }

        def xPerBlock = params.int("xPerBlock")
        def extractions = [:]
        def excludedPlateExtractionsMap = [:]

        plateLayouts.each{

            def excludedPlateExtractions = []
            for(int extraction in 1..params.int("numOfExtractions")){
                def extractionExcluded = "Plate_"+it.toString()+"|Extraction_"+extraction+"|Field"
                excludedPlateExtractions << params.boolean(extractionExcluded)
                excludedPlateExtractionsMap.put(extractionExcluded, params.boolean(extractionExcluded))
            }
            extractions.put(it, excludedPlateExtractions)
        }

        if(!params.title || params.title == "")
        {
            flash.message = "You have to give a title to this layout!"
            render (view: "spotterSettings", model: [layouts:  layouts, excludedPlateExtractions: excludedPlateExtractionsMap])
            return
        }
        //check if title is taken
        if(SlideLayout.findByTitle(params.title))
        {
            flash.message = "Please select another title (this one already exists)."
            render (view: "spotterSettings", model: [layouts:  layouts, excludedPlateExtractions: excludedPlateExtractionsMap])
            return
        }

        def slideLayout
        try{
            slideLayout = plateImportService.importPlates(params.title, plateLayouts, extractions, params.spottingOrientation,
                    params.extractorOperationMode,
                    params.depositionPattern, xPerBlock,
                    params.bottomLeftDilution, params.topLeftDilution,
                    params.topRightDilution, params.bottomRightDilution)
        } catch(Exception e)
        {
            flash.message = "Import failed with exception: " + e.getMessage()
            redirect(action: "plateImport")
            return
        }

        slideLayout.lastUpdatedBy = springSecurityService.currentUser
        slideLayout.createdBy = springSecurityService.currentUser

        if (slideLayout.save(flush: true, failOnError: true)) {
            redirect(controller: "slideLayout", action: "show", id: slideLayout.id)
        }
        else{
            flash.message = "import succeeded, but persisting the slide layout failed: " + slideLayout.errors.toString()
            redirect(action:  "plateImport")
        }
    }

    def blockSettings(){
        def rowWise = false

        if(params.blockOrder == "left-to-right"){
            rowWise = true
        }

        render template: "blockSettings", model: [rowWise: rowWise]
    }
}
