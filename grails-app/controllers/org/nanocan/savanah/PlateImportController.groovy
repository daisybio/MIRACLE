package org.nanocan.savanah

import org.nanocan.savanah.experiment.Experiment
import org.nanocan.savanah.plates.Plate
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class PlateImportController {

    def plateImportService

    def plateImport(){
        def plates

        if(params.experiment){
            def experiment  = Experiment.get(params.experiment)
            plates = experiment.plates
        }

        else plates = Plate.list()

        [experiments: Experiment.list(), plates: plates]
    }

    def platesOrdered(){

        if (!params.list("plate[]"))
            render "No plates have been selected"

        else{
            def plates = params.list("plate[]").collect{
                Plate.get(it)
            }

            render(template: "sortedPlates", model: [sortedPlates: plates])
        }
    }

    def importSettings(){
        def plates = params.plateOrder
        [plates: plates]
    }

    def processPlates(){

        def plates = params.list("plates")
        def extractions = [:]

        plates.each{
            def excludedPlateExtractions = []
            for(int extraction in 1..params.int("numOfExtractions")){
                excludedPlateExtractions << params.boolean("Plate_"+it.toString()+"|Extraction_"+extraction+"|Field")
            }
            extractions.put(it, excludedPlateExtractions)
        }
        println extractions
        plateImportService.importPlates(plates, extractions, params.spottingOrientation,
                params.extractorOperationMode,
                params.depositionPattern, params.columnsPerBlock,
                params.bottomLeftDilution, params.topLeftDilution,
                params.topRightDilution, params.bottomRightDilution)

        render "success"
    }

    def blockSettings(){
        def rowWise = false

        if(params.blockOrder == "left-to-right"){
            rowWise = true
        }

        render template: "blockSettings", model: [rowWise: rowWise]
    }
}
