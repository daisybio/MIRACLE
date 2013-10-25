package org.nanocan.rppa.webflow

import grails.converters.JSON


import org.nanocan.rppa.layout.CellLine
import org.nanocan.rppa.scanner.ResultFile
import org.nanocan.rppa.scanner.Slide
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONArray
import grails.converters.XML

class IOService {
	def grailsApplication
	def idCountingService

	def exportFlowAsJSON(Object flow){
		def res = ""

		def plateLayouts = flow.listOfPlateLayouts?.values()?.toList() as JSON
		res += plateLayouts.toString()

		def cellLines = flow.listOfCellLines?.values()?.toList() as JSON
		res += "\r\n" + cellLines.toString()
		
		def slideLayouts = flow.listOfSlideLayouts?.values()?.toList() as JSON
		res += "\r\n" + slideLayouts.toString()
		
		def dilutionFactors = flow.listOfDilutionFactors?.values()?.toList() as JSON
		res += "\r\n" + dilutionFactors.toString()
		
		def inducers = flow.listOfInducers?.values()?.toList() as JSON
		res += "\r\n" + inducers.toString()
		
		def lysisBuffers = flow.listOfLysisBuffers?.values()?.toList() as JSON
		res += "\r\n" + lysisBuffers.toString()
		
		def numberOfCellsSeeded = flow.listOfNumberOfCellsSeeded?.values()?.toList() as JSON
		res += "\r\n" + numberOfCellsSeeded.toString()
		
		def listOfSpotTypes = flow.listOfSpotTypes?.values()?.toList() as JSON
		res += "\r\n" + listOfSpotTypes.toString()
		
		def treatments = flow.listOfTreatments?.values()?.toList() as JSON
		res += "\r\n" + treatments.toString()
		
		def samples = flow.listOfSamples?.values()?.toList() as JSON
		res += "\r\n" + samples.toString()
		
		def identifiers = flow.listOfIdentifiers?.values()?.toList() as JSON
		res += "\r\n" + identifiers.toString()
		
		def antibodys = flow.listOfAntibodys?.values()?.toList() as JSON
		res += "\r\n" + antibodys.toString()
		
		def slides = flow.listOfSlides?.values()?.toList() as JSON
		res += "\r\n" + slides.toString()
		
		/*
				 
		 */
		
		
		return res
	}
	
	def parseFileAsFlow(String res, Object flow){
		res = "[{\"class\":\"org.nanocan.rppa.layout.CellLine\",\"id\":1,\"name\":\"ce1\",\"color\":\"#bd00bd\"},{\"class\":\"org.nanocan.rppa.layout.CellLine\",\"id\":2,\"name\":\"dse\",\"color\":\"#008be8\"},{\"class\":\"org.nanocan.rppa.layout.CellLine\",\"id\":1,\"name\":\"ce1\",\"color\":\"#bd00bd\"},{\"class\":\"org.nanocan.rppa.layout.CellLine\",\"id\":2,\"name\":\"dse\",\"color\":\"#008be8\"}]"
		def int i = 1
		res.eachLine {
			//println "json obj: " + i + "  " + it
			def listMember = JSON.parse(it) as JSONArray
			
			for(Object o in listMember){
				println o.class
				switch (o.class){
					case "org.nanocan.rppa.layout.CellLine":
						def c = new CellLine()
						c.name = o.name
						c.color = o.color
						c.id = o.id

						flow.listOfCellLines.put(o.id as int, c)			//Remember id should be int when you use it as key in a hashmap!!
						def newId = idCountingService.getId("CellLine")		//This keeps track of id's, so no conflicts can happen when you create a new object of the same type
																			//If CellLine exists with same id, it gets overwritten
						break
					
					case "org.nanocan.rppa.layout.PlateLayout":
						println o.id
						break
				
				}
				/*
				if(o.class == "org.nanocan.rppa.layout.CellLine"){
					def c = new CellLine()
					c.name = o.name
					c.color = o.color
					c.id = o.id

					flow.listOfCellLines.put(o.id as int, c)			//Remember id should be int when you use it as key in a hashmap!!
					def newId = idCountingService.getId("CellLine")		//This keeps track of id's, so no conflicts can happen when you create a new object of the same type
																		//If CellLine exists with same id, it gets overwritten
				}
				if(o.class == "org.nanocan.rppa.layout.PlateLayout"){
					//println o.wells
					
					for(Object w in o.wells){
						//println w
					}
				}
				*/
			}
			i++
		}
		//return flow
	}
	
	def dealWithFileUploads(def request, Slide slideInstance)
	{

		CommonsMultipartFile resultFile
		CommonsMultipartFile resultImage
		CommonsMultipartFile protocol



		/* if new files have been uploaded we take them instead */
		if(request instanceof MultipartHttpServletRequest)
		{

			MultipartHttpServletRequest mpr = (MultipartHttpServletRequest)request;

			resultFile = (CommonsMultipartFile) mpr.getFile("resultFileInput");
			resultImage = (CommonsMultipartFile) mpr.getFile("resultImageInput");
			protocol = (CommonsMultipartFile) mpr.getFile("protocolInput");

			if(!resultFile.empty) slideInstance.resultFile = createResultFile(resultFile, "Result")

			if(!resultImage.empty) slideInstance.resultImage = createResultFile(resultImage, "Image")

			if(!protocol.empty) slideInstance.protocol = createResultFile(protocol, "Protocol")
		}

		return slideInstance
	}

	def createResultFile(def resultFile, String type)
	{
		if(!resultFile.empty) {
			def currentDate = new java.util.Date()
			long timestamp = currentDate.getTime()
			def filePath = grailsApplication.config.rppa.upload.directory + "temp\\" + timestamp.toString() + "_" + resultFile.originalFilename


			resultFile.transferTo( new File(filePath) )

			def newResultFile = new ResultFile(fileType: type, fileName: (resultFile.originalFilename as String), filePath: filePath, dateUploaded:  currentDate as Date)

			if(newResultFile.save(flush: true))
			{
				log.info "saving of file ${filePath} was successful"
				return newResultFile
			}

			else
			{
				log.warn "could not save file to ${filePath}."
				return null
			}

		}
	}
}
