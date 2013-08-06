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
				well.id =  i
			
				plateLayout.addToWells(well)
				//println "id: " + well.id + " Col: " + col + " Row: " + row
				
				i++
			}
		}
		return null
	}
	def updateWellProperty(params, plateLayout, cellLines){
		def wellProp = params.wellProperty

		println "Well properties: " + wellProp

		println "plateLayoutcontroller plateLayout: " + plateLayout

		params.remove("action")
		params.remove("controller")
		params.remove("wellProperty")
		params.remove("plateLayout")

		if(params.size() == 0) render "Nothing to do"

		println "size: " + params.keySet().size()
		
		
		
		/*def x = 0
		params.each{key, value ->
			if (value != "" && key.toString().length() < 5) {
				println "index: " + x + " Key: " + key + " Value: " + value
			}
			x++
		}
		*/

		updateWellProperties(params, wellProp, plateLayout, cellLines)

		//progressService.setProgressBarValue("update${plateLayout}", 100)
		//render "Save successful"

	}

	def updateWellProperties(params, wellProp, plateLayout, cellLines){
		def numberOfWells = params.keySet().size()
		def currentWell = 0
		def wells = plateLayout.wells.toList().sort()
		 
		println "cellLine List: " + cellLines
		println "c: " + cellLines[1]

		params.each{key, value ->
			if (value != "" && key.toString().length() < 5) {				
				for(int i = 0;i < wells.size();i++){
					if(wells.get(i).id as int == key as int){
						
						println "index: " + i + " id: " + wells.get(i).id + " Value: " + wells.get(i)
						
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
				
				/*def i = 0
				wells.each {
					if(wells.get(i).id == key){
						def well = wells.get(i)
						println well
						if (value as Long == -1) well.properties[wellProp] = null
						else{
							def x = 0
							cellLines.t
							if(i < cellLines.toList().size()){
								if(cellLines[i].id == value){
									def cellLine = cellLines[i]
									println cellLine
								}
							}
							well.properties[wellProp] = cellLine
						}
						plateLayout.wells.toList().sort().set(i, well)

					}
					i++
				}*/
	
				 //def well = wells.get(key as int)
				 //if (value as Long == -1) well.properties[wellProp] = null
				 //else{ 
				 //well.properties[wellProp] = cellLines[value as int]
				 //plateLayout.wells.toList().sort().set(key as int, well)
				 //println "wellpop: " + well.properties[wellProp]
				 //println "cellline from list : " + cellLines[value as int]
				 //}
				 
			}
		}
	}
}