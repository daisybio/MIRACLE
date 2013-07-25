package org.nanocan.rppa.webflow

import org.nanocan.rppa.layout.PlateLayout
import org.nanocan.rppa.layout.WellLayout

class FlowPlateLayoutService {
	def progressService
	def idCountingService
	
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
	def updateWellProperty(params){
		def wellProp = params.wellProperty

		println "Well properties: " + wellProp

		def plateLayout = params.plateLayout

		println "plateLayoutcontroller plateLayout: " + plateLayout

		params.remove("action")
		params.remove("controller")
		params.remove("wellProperty")
		params.remove("plateLayout")

		if(params.size() == 0) render "Nothing to do"

		println params.keySet().sort()
		println params.keySet().size()
		
		params.each{key, value ->
			if (value != "") {
				println "key: " + key + "value: " + value
			}
		}
		//updateWellProperties(params, wellProp, plateLayout)

		//progressService.setProgressBarValue("update${plateLayout}", 100)
		//render "Save successful"
		
	}
	
	def updateWellProperties(params, wellProp, plateLayout){
		def numberOfWells = params.keySet().size()
		def currentWell = 0
		params.each {key, value ->
			if (value != "") {
				def well = WellLayout.get(key as Long)

				def classPrefix = "org.nanocan.rppa.layout."
				if(wellProp == "sample") classPrefix = "org.nanocan.rppa.rnai."
				if (value as Long == -1) well.properties[wellProp] = null
				else well.properties[wellProp] = grailsApplication.getDomainClass(classPrefix + wellProp.toString().capitalize()).clazz.get(value as Long)
				
				//well.save()
			}

			progressService.setProgressBarValue("update${plateLayout}", (currentWell / numberOfWells * 100))
			currentWell++

		}
	}
}