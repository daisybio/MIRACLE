package org.nanocan.rppa.webflow

import org.nanocan.rppa.layout.PlateLayout
import org.nanocan.rppa.layout.WellLayout

class FlowPlateLayoutService {
	def createWellLayouts(PlateLayout plateLayout) {		
		for (int col = 1; col <= plateLayout.cols; col++) {
			for (int row = 1; row <= plateLayout.rows; row++) {
				plateLayout.addToWells(new WellLayout(col: col, row: row, layout: plateLayout))
			}
		}
		return null
	}
	def updateWellProperties(params, wellProp, plateLayout){
		def numberOfWells = params.keySet().size()
		def currentWell = 0

		params.each {key, value ->
			if (value != "") {
				def well = plateLayout.wells[params.id]

				def classPrefix = "org.nanocan.rppa.layout."
				if(wellProp == "sample") classPrefix = "org.nanocan.rppa.rnai."
				if (value as Long == -1) well.properties[wellProp] = null
				else well.properties[wellProp] = grailsApplication.getDomainClass(classPrefix + wellProp.toString().capitalize()).clazz.get(value as Long)
				plateLayout.wells[well.id] = well
			}
			
			def well = WellLayout.get(key as Long)
			println "wellid: " + plateLayout.wells[well.id].id + "wellS id: " + well.id
			
			progressService.setProgressBarValue("update${plateLayout}", (currentWell / numberOfWells * 100))
			currentWell++
		}
	}
}
