package org.nanocan.rppa.webflow

import org.nanocan.rppa.layout.PlateLayout
import org.nanocan.rppa.layout.WellLayout
import org.nanocan.rppa.layout.CellLine

class FlowPlateLayoutService {
	def progressService
	def idCountingService
	def grailsApplication

	def createWellLayouts(PlateLayout plateLayout) {
		def i = 1
		for (int col = 1; col <= plateLayout.cols; col++) {
			for (int row = 1; row <= plateLayout.rows; row++) {
				def well = new WellLayout(col: col, row: row, layout: plateLayout)
				def newId = idCountingService.getId("WellLayout")
				well.id =  newId
				

				plateLayout.addToWells(well)

				i++
			}
		}
		return null
	}
	def updateWellProperty(params, plateLayout,cellLines,inducers,spotTypes,treatments,numberOfCellsSeeded,samples){
		def wellProp = params.wellProperty
		params.remove("action")
		params.remove("controller")
		//params.remove("wellProperty")
		params.remove("plateLayout")

		if(params.size() == 0) render "Nothing to do"
		
		def numberOfWells = params.keySet().size()
		def currentWell = 0
		def wells = plateLayout.wells.toList().sort()
		println "wellProp: " + wellProp
		println "cellLine List: " + cellLines
		println "c: " + cellLines[1]
		
		if(wellProp == "cellLine"){
			params.each{key, value ->
				if (value != "" && key.toString().length() < 5) {
					for(int i = 0;i < wells.size();i++){
						if(wells.get(i).id as int == key as int){
							def well = wells.get(i)
							if (value as Long == -1) well.properties[wellProp] = null
							else{
								def cellLine = cellLines[value as int]
								println " CellLine" + cellLine
								well.properties[wellProp] = cellLine
							}
							plateLayout.wells.toList().sort().set(i, well)
						}
					}
				}
			}
		}
		else if (wellProp == "inducer"){
			params.each{key, value ->
				if (value != "" && key.toString().length() < 5) {
					for(int i = 0;i < wells.size();i++){
						if(wells.get(i).id as int == key as int){
							def well = wells.get(i)
							if (value as Long == -1) well.properties[wellProp] = null
							else{
								def inducer = inducers[value as int]
								well.properties[wellProp] = inducer
							}
							plateLayout.wells.toList().sort().set(i, well)
						}
					}
				}
			}
		}
		else if (wellProp == "spotType"){
			params.each{key, value ->
				if (value != "" && key.toString().length() < 5) {
					for(int i = 0;i < wells.size();i++){
						if(wells.get(i).id as int == key as int){
							def well = wells.get(i)
							if (value as Long == -1) well.properties[wellProp] = null
							else{
								def spotType = spotTypes[value as int]
								well.properties[wellProp] = spotType
							}
							plateLayout.wells.toList().sort().set(i, well)
						}
					}
				}
			}
		}
		else if (wellProp == "treatment"){
			params.each{key, value ->
				if (value != "" && key.toString().length() < 5) {
					for(int i = 0;i < wells.size();i++){
						if(wells.get(i).id as int == key as int){
							def well = wells.get(i)
							if (value as Long == -1) well.properties[wellProp] = null
							else{
								def treatment = treatments[value as int]
								well.properties[wellProp] = treatment
							}
							plateLayout.wells.toList().sort().set(i, well)
						}
					}
				}
			}
		}
		else if (wellProp == "numberOfCellsSeeded"){
			params.each{key, value ->
				if (value != "" && key.toString().length() < 5) {
					for(int i = 0;i < wells.size();i++){
						if(wells.get(i).id as int == key as int){
							def well = wells.get(i)
							if (value as Long == -1) well.properties[wellProp] = null
							else{
								def numberOfCellsSeede = numberOfCellsSeeded[value as int]
								well.properties[wellProp] = numberOfCellsSeede
							}
							plateLayout.wells.toList().sort().set(i, well)
						}
					}
				}
			}
		}
		else if (wellProp == "sample"){
			params.each{key, value ->
				if (value != "" && key.toString().length() < 5) {
					for(int i = 0;i < wells.size();i++){
						if(wells.get(i).id as int == key as int){
							def well = wells.get(i)
							if (value as Long == -1) well.properties[wellProp] = null
							else{
								def sample = samples[value as int]
								well.properties[wellProp] = sample
							}
							plateLayout.wells.toList().sort().set(i, well)
						}
					}
				}
			}
		}
		
		progressService.setProgressBarValue("update${plateLayout}", 100)
		//render "Save successful"
	}
}