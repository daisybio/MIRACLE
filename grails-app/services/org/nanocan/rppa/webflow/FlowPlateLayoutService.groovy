package org.nanocan.rppa.webflow

import org.nanocan.rppa.layout.PlateLayout
import org.nanocan.rppa.layout.WellLayout
import org.nanocan.rppa.layout.CellLine

class FlowPlateLayoutService {
	def progressService
	def idCountingService
	def grailsApplication

	def createWellLayouts(PlateLayout plateLayout) {
		for (int col = 1; col <= plateLayout.cols; col++) {
			for (int row = 1; row <= plateLayout.rows; row++) {
				def well = new WellLayout(col: col, row: row, layout: plateLayout)
				well.id =  idCountingService.getId("Well")
				plateLayout.addToWells(well)
			}
		}
		return null
	}
	def updateWellProperty(params, plateLayout, cellLines){
		def wellProp = params.wellProperty

		println "Well properties: " + wellProp

		//def plateLayout = params.plateLayout

		println "plateLayoutcontroller plateLayout: " + plateLayout

		params.remove("action")
		params.remove("controller")
		params.remove("wellProperty")
		params.remove("plateLayout")

		if(params.size() == 0) render "Nothing to do"

		println "size: " + params.keySet().size()


		params.each{key, value ->
			if (value != "" && key.toString().length() < 5) {
				println "Key: " + key + " Value: " + value
			}
		}


		updateWellProperties(params, wellProp, plateLayout, cellLines)

		//progressService.setProgressBarValue("update${plateLayout}", 100)
		//render "Save successful"

	}

	def updateWellProperties(params, wellProp, plateLayout, cellLines){
		def numberOfWells = params.keySet().size()
		def currentWell = 0
		def wells = plateLayout.wells.toList()
		println "wells: " + wells.each {this.id}
		println "cellLine List: " + cellLines
		println "c: " + cellLines[1]
		
		params.each{key, value ->
			if (value != "" && key.toString().length() < 5) {
				def well = wells.get(key as int)

				if (value as Long == -1) well.properties[wellProp] = null
				else{ 
					well.properties[wellProp] = cellLines[value as int]
					plateLayout.wells.toList().set(key as int, well)
					
					println "wellpop: " + well.properties[wellProp]
					println "cellline from list : " + cellLines[value as int]
				}
			}
		}
	}
}