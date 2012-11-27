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

        render template: "/spotting/plateLayoutList", model: ["layouts": layouts, "ulid": "unselectedSavanahPlateLayouts", "prefix":"layout_savanah"]
    }

    def filterMiraclePlateLayoutsByProject(){
        def layouts = org.nanocan.rppa.project.Project.findByProjectTitle(params.project)

        if(!layouts) layouts = org.nanocan.rppa.layout.PlateLayout.list()
        else layouts = layouts.plateLayouts

        render template: "/spotting/plateLayoutList", model: ["layouts": layouts, "ulid": "unselectedMiraclePlateLayouts", "prefix":"layout_miracle"]
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
        plateImportService.createMatchListsForSavanahLayouts(plateLayouts, true)
    }


    def blockSettings(){
        def rowWise = false

        if(params.blockOrder == "left-to-right"){
            rowWise = true
        }

        render template: "blockSettings", model: [rowWise: rowWise]
    }
}
