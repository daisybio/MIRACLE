import org.nanocan.rppa.scanner.Antibody
import org.nanocan.rppa.scanner.ResultFileConfig

import org.nanocan.rppa.scanner.Slide
import org.nanocan.rppa.scanner.ResultFile
import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.rppa.layout.CellLine
import org.nanocan.rppa.layout.LysisBuffer
import org.nanocan.rppa.layout.Dilution

import org.nanocan.rppa.layout.SpotType
import org.nanocan.rppa.layout.Inducer
import org.nanocan.rppa.rnai.Sample
import grails.util.GrailsUtil
import org.nanocan.rppa.security.Role
import org.nanocan.rppa.security.Person
import org.nanocan.rppa.security.PersonRole
import org.nanocan.rppa.project.Project
import grails.converters.JSON
import org.nanocan.rppa.scanner.Spot
import org.nanocan.rppa.scanner.BlockShift
import org.nanocan.savanah.plates.PlateLayout
import org.nanocan.savanah.plates.WellLayout
import org.nanocan.savanah.plates.Plate

import org.nanocan.rppa.layout.PlateLayout
import org.nanocan.rppa.layout.WellLayout
import org.nanocan.rppa.layout.LayoutSpot
import org.nanocan.rppa.layout.NumberOfCellsSeeded
import org.nanocan.rppa.layout.Treatment
import org.nanocan.rppa.rnai.Identifier
//import org.springframework.webflow.core.collection.LocalAttributeMap


class BootStrap {

    def slideLayoutService
    def depositionService
    def grailsApplication

    def init = { servletContext ->

        switch (GrailsUtil.environment) {
            case "development":

                initUserbase()
                //initMIRACLE()
                //initSAVANAH()

                break

            case "test":
                initUserbase()
                break

            case "migrate":
                initUserbase()
                break

            case "standalone":
                initUserbase()
                break
        }

        String imagezoomFolder = grailsApplication.config.rppa.imagezoom.directory
        new File(imagezoomFolder).mkdir()

        /* custom JSON output */
        JSON.registerObjectMarshaller(Spot) {
            def returnArray = [:]
            returnArray['FG'] = it.FG
            returnArray['BG'] = it.BG
            returnArray['Signal'] = it.signal
            returnArray['Block'] = it.block
            returnArray['Row'] = it.row
            returnArray['Column'] = it.col
            returnArray['SampleName'] = it.layoutSpot?.sample?.name?:"NA"
            returnArray['SampleType'] = it.layoutSpot?.sample?.type?:"NA"
            returnArray['TargetGene'] = it.layoutSpot?.sample?.targetGene?:"NA"
            returnArray['CellLine'] = it.layoutSpot?.cellLine?.name?:"NA"
            returnArray['LysisBuffer'] = it.layoutSpot?.lysisBuffer?.name?:"NA"
            returnArray['DilutionFactor'] = it.layoutSpot?.dilutionFactor?.dilutionFactor?:"NA"
            returnArray['Inducer'] = it.layoutSpot?.inducer?.name?:"NA"
            returnArray['Treatment'] = it.layoutSpot?.treatment?.name?:"NA"
            returnArray['SpotType'] = it.layoutSpot?.spotType?.name?:"NA"
            returnArray['SpotClass'] = it.layoutSpot?.spotType?.type?:"NA"
            returnArray['NumberOfCellsSeeded'] = it.layoutSpot?.numberOfCellsSeeded?.name?:"NA"
            returnArray['Flag'] = it.flag
            returnArray['Diameter'] = it.diameter

            return returnArray
        }

        JSON.registerObjectMarshaller(BlockShift) {

            def returnArray = [:]
            returnArray['Block'] = it.blockNumber
            returnArray['hshift'] = it.horizontalShift
            returnArray['vshift'] = it.verticalShift

            return returnArray
        }
		
		JSON.registerObjectMarshaller(PlateLayout) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['name'] = it.name
			returnArray['format'] = it.format
			returnArray['cols'] = it.cols
			returnArray['rows'] = it.rows
			returnArray['wells'] = it.wells
			
			return returnArray
		}
		
		JSON.registerObjectMarshaller(WellLayout) {
			def returnArray = [:]
			
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['col'] = it.col
			returnArray['row'] = it.row
			if((it.numberOfCellsSeeded?:"NA")!="NA") returnArray['NumberOfCellsSeeded'] = it.numberOfCellsSeeded
			if((it.cellLine?:"NA")!="NA") 	returnArray['CellLine'] = it.cellLine
			if((it.inducer?:"NA")!="NA") 	returnArray['Inducer'] = it.inducer
			if((it.treatment?:"NA")!="NA") 	returnArray['Treatment'] = it.treatment
			if((it.spotType?:"NA")!="NA") 	returnArray['SpotType'] = it.spotType
			if((it.sample?:"NA")!="NA") 	returnArray['Sample'] = it.sample
			
			return returnArray
		}
		
		JSON.registerObjectMarshaller(SlideLayout) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			
			returnArray['dateCreated'] = it.dateCreated
			returnArray['lastUpdated'] = it.lastUpdated
			returnArray['createdBy'] = it.createdBy
			returnArray['lastUpdatedBy'] = it.lastUpdatedBy
			
			returnArray['title'] = it.title
			returnArray['columnsPerBlock'] = it.columnsPerBlock
			returnArray['rowsPerBlock'] = it.rowsPerBlock
			returnArray['numberOfBlocks'] = it.numberOfBlocks
			returnArray['blocksPerRow'] = it.blocksPerRow
			returnArray['depositionPattern'] = it.depositionPattern
			returnArray['sampleSpots'] = it.sampleSpots
			
			
			return returnArray
		}
		
		JSON.registerObjectMarshaller(Slide) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['dateOfStaining'] = it.dateOfStaining
			returnArray['dateCreated'] = it.dateCreated
			
			returnArray['lastUpdatedBy'] = it.lastUpdatedBy
			returnArray['laserWavelength'] = it.laserWavelength
			returnArray['photoMultiplierTube'] = it.photoMultiplierTube
			returnArray['resultFile'] = it.resultFile
			returnArray['layout'] = it.layout
			returnArray['spots'] = it.spots
			returnArray['blockShifts'] = it.blockShifts
			returnArray['antibody'] = it.antibody
			
			
			if((it.title?:"NA")!="NA") returnArray['title'] = it.title
			if((it.lastUpdated?:"NA")!="NA") returnArray['lastUpdated'] = it.lastUpdated
			if((it.createdBy?:"NA")!="NA") returnArray['createdBy'] = it.createdBy
			if((it.experimenter?:"NA")!="NA") returnArray['experimenter'] = it.experimenter
			if((it.protocol?:"NA")!="NA") returnArray['protocol'] = it.protocol
			if((it.lastConfig?:"NA")!="NA") returnArray['lastConfig'] = it.lastConfig
			if((it.barcode?:"NA")!="NA") returnArray['barcode'] = it.barcode
			if((it.resultImage?:"NA")!="NA") returnArray['resultImage'] = it.resultImage
			if((it.comments?:"NA")!="NA") returnArray['comments'] = it.comments
			
			
			return returnArray
		}
		
		JSON.registerObjectMarshaller(ResultFile) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['fileName'] = it.fileName
			returnArray['filePath'] = it.filePath	
			returnArray['dateUploaded'] = it.dateUploaded
			returnArray['fileType'] = it.fileType
			
			
			return returnArray
		}
		
		JSON.registerObjectMarshaller(LayoutSpot) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['col'] = it.col
			returnArray['row'] = it.row
			returnArray['block'] = it.block
			returnArray['belongsTo'] = it.belongsTo
			
			
			if((it.cellLine?:"NA")!="NA") returnArray['cellLine'] = it.cellLine
			if((it.lysisBuffer?:"NA")!="NA") 	returnArray['lysisBuffer'] = it.lysisBuffer
			if((it.dilutionFactor?:"NA")!="NA") 	returnArray['dilutionFactor'] = it.dilutionFactor
			if((it.inducer?:"NA")!="NA") 	returnArray['inducer'] = it.inducer
			if((it.spotType?:"NA")!="NA") 	returnArray['spotType'] = it.spotType
			if((it.sample?:"NA")!="NA") 	returnArray['sample'] = it.sample
			if((it.treatment?:"NA")!="NA") 	returnArray['treatment'] = it.treatment
			if((it.numberOfCellsSeeded?:"NA")!="NA") 	returnArray['numberOfCellsSeeded'] = it.numberOfCellsSeeded
			
			return returnArray
		}
		
		JSON.registerObjectMarshaller(CellLine) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['name'] = it.name
			returnArray['color'] = it.color

			return returnArray
		}
		
		JSON.registerObjectMarshaller(Dilution) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['dilutionFactor'] = it.dilutionFactor
			returnArray['color'] = it.color

			return returnArray
		}
		
		JSON.registerObjectMarshaller(Inducer) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['name'] = it.name
			returnArray['concentration'] = it.concentration
			returnArray['concentrationUnit'] = it.concentrationUnit
			returnArray['color'] = it.color

			return returnArray
		}
		
		JSON.registerObjectMarshaller(LysisBuffer) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['name'] = it.name
			returnArray['concentration'] = it.concentration
			returnArray['concentrationUnit'] = it.concentrationUnit
			returnArray['color'] = it.color

			return returnArray
		}
		
		JSON.registerObjectMarshaller(LysisBuffer) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['name'] = it.name
			returnArray['concentration'] = it.concentration
			returnArray['concentrationUnit'] = it.concentrationUnit
			returnArray['color'] = it.color

			return returnArray
		}
		
		JSON.registerObjectMarshaller(NumberOfCellsSeeded) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['name'] = it.name
			returnArray['color'] = it.color

			return returnArray
		}
		
		JSON.registerObjectMarshaller(SpotType) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['name'] = it.name
			returnArray['type'] = it.type
			returnArray['color'] = it.color

			return returnArray
		}
		
		JSON.registerObjectMarshaller(Treatment) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['name'] = it.name
			returnArray['comments'] = it.comments
			returnArray['color'] = it.color

			return returnArray
		}
		
		JSON.registerObjectMarshaller(Sample) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['name'] = it.name
			returnArray['type'] = it.type	
			returnArray['targetGene'] = it.targetGene
			returnArray['color'] = it.color

			returnArray['identifiers'] = it.identifiers
			
			return returnArray
		}
		
		JSON.registerObjectMarshaller(Identifier) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['name'] = it.name
			returnArray['accessionNumber'] = it.accessionNumber	
			returnArray['type'] = it.type
			returnArray['sample'] = it.sample
			
			return returnArray
		}
		
		JSON.registerObjectMarshaller(Antibody) {
			def returnArray = [:]
			returnArray['class'] = it.class
			returnArray['id'] = it.id
			returnArray['name'] = it.name
			returnArray['concentration'] = it.concentration?:"NA"
			returnArray['concentrationUnit'] = it.concentrationUnit
			returnArray['comments'] = it.comments?:"NA"
			
			return returnArray
		}
    }

    private void initSAVANAH(){
        def cellLineA = new org.nanocan.savanah.attributes.CellLine(color: "#aa0000", name: "MCF7").save(flush: true, failOnError: true)
        def cellLineB = new org.nanocan.savanah.attributes.CellLine(color: "#00aa00", name: "MCF12").save(flush: true, failOnError: true)

        def plateLayout96 = new PlateLayout(name: "test layout 96", format: "96-well", cols: 12, rows: 8)
        plateLayout96.save()

        for (int col = 1; col <= plateLayout96.cols; col++) {
            for (int row = 1; row <= plateLayout96.rows; row++) {
                plateLayout96.addToWells(new WellLayout(col: col, row: row, layout: plateLayout96, cellLine: cellLineA))
            }
        }
        plateLayout96.save(flush:true, failOnError: true)

        def plateLayout96b = new PlateLayout(name: "test layout 96b", format: "96-well", cols: 12, rows: 8)
        plateLayout96b.save()

        for (int col = 1; col <= plateLayout96b.cols; col++) {
            for (int row = 1; row <= plateLayout96b.rows; row++) {
                plateLayout96b.addToWells(new WellLayout(col: col, row: row, layout: plateLayout96b, cellLine: cellLineB))
            }
        }
        plateLayout96b.save(flush:true, failOnError: true)

        int i = 1

        for(name in ["a", "b", "c", "d"]){
            def plate96

            if(i++ % 2 == 1) plate96 = new Plate(cols: 12, rows:  8, barcode: "${name}96bc", family: "daughter", format: "96-well", name: "${name}96", layout: plateLayout96)
            else plate96 = new Plate(cols: 12, rows:  8, barcode: "${name}96bc", family: "daughter", format: "96-well", name: "${name}96", layout: plateLayout96b)
            plate96.save(flush: true)
        }
    }

    private void initMIRACLE(){
        new Dilution(color: "#aa0000", dilutionFactor: 0.4).save(flush: true, failOnError: true)
        new Dilution(color: "#00aa00", dilutionFactor: 0.16).save(flush:true, failOnError: true)
        new Dilution(color: "#0000aa", dilutionFactor: 0.064).save(flush: true, failOnError: true)
        new Dilution(color: "#aaaa00", dilutionFactor: 1.0).save(flush: true, failOnError: true)
    }

    private void initUserbase(){

        def adminRole = Role.findByAuthority("ROLE_ADMIN")
        if(!adminRole)  adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true, failOnError: true)
        def userRole = Role.findByAuthority("ROLE_USER")
        if(!userRole) userRole= new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)

        if(!Person.findByUsername("user")){
            def testUser = new Person(username: 'user', enabled: true, password: 'password')
            testUser.save(flush: true, failOnError: true)
            PersonRole.create testUser, userRole, true
        }

        if(!Person.findByUsername("mlist")){
            def adminUser = new Person(username: 'mlist', enabled: true, password: 'password')
            adminUser.save(flush: true, failOnError: true)
            PersonRole.create adminUser, adminRole, true
            PersonRole.create adminUser, userRole, true
        }
    }

    private void initSampleData() {

        def primaryAB = new Antibody(name: "btubulin 1", concentration: 5, concentrationUnit: "mM").save(flush:true, failOnError: true)
        def primaryAB2 = new Antibody(name: "btubulin 2", concentration: 5, concentrationUnit: "mM").save(flush:true, failOnError: true)

        def dilution = new Dilution(dilutionFactor:  1.0, color: "#e0e0e0").save(flush:true, failOnError: true)
        def dilutionA = new Dilution(dilutionFactor:  0.5, color: "#aa0000").save(flush:true, failOnError: true)
        def dilutionB = new Dilution(dilutionFactor:  0.25, color: "#00bb00").save(flush:true, failOnError: true)
        def dilutionC = new Dilution(dilutionFactor:  0.125, color: "#0000aa").save(flush:true, failOnError: true)

        new SpotType(name: "LB", type: "Buffer", color:  "#550000").save(flush:true, failOnError: true)
        new SpotType(name:  "IgG10", type: "Control", color:  "#000055").save(flush:true, failOnError: true)
        new SpotType(name: "IgG100", type: "Control", color:  "#0000aa").save(flush:true, failOnError: true)
        new SpotType(name: "Sample", type: "Sample", color:  "#005500").save(flush:true, failOnError: true)

        new Inducer(name: "Dox .5", concentration: 0.5, concentrationUnit: "mM", color: "#aa0000").save(flush:true, failOnError: true)
        new Inducer(name: "Dox .1", concentration: 0.1, concentrationUnit: "mM", color: "#550000").save(flush:true, failOnError: true)

        new Sample(name: "siRNA1", type: "siRNA", targetGene: "Gene1", color: "#a0a000").save(flush:true, failOnError: true)
        new Sample(name: "siRNA2", type: "siRNA", targetGene: "Gene2", color: "#00a0a0").save(flush:true, failOnError: true)
        new Sample(name: "siRNA3", type:  "siRNA", targetGene: "Gene3", color: "#a000a0").save(flush:true, failOnError: true)

        def resultFileConfig = new ResultFileConfig(
                name : "Default Config",
                blockCol: 'F',
                rowCol: 'H',
                columnCol: 'G',
                fgCol: 'M',
                bgCol: 'S',
                flagCol: 'L',
                xCol: 'I',
                yCol: 'J',
                diameterCol: 'K',
                skipLines: 25
        ).save(flush:true, failOnError: true)

        String fileName = "sampleData/2012-03-28 b-tubulin abcam abnova original.xls"
        String fileNameTwoColumns = "sampleData/2012-01-19 MLP - orginal.xls"

        def resultFile = new ResultFile(fileType: "Result", fileName: "2012-03-28 b-tubulin abcam abnova original.xls",
                filePath:  fileName, dateUploaded: new Date()).save(flush: true, failOnError: true)

        def resultFileTwoColums = new ResultFile(fileType: "Result", fileName: "2012-01-19 MLP - orginal.xls",
                filePath:  fileNameTwoColumns, dateUploaded: new Date()).save(flush: true, failOnError: true)

        def person = Person.get(1)

        def slideLayout = new SlideLayout(columnsPerBlock: 1, rowsPerBlock: 72, numberOfBlocks: 12,
                title: "Default Layout", depositionPattern: "[4,4,2,2,1,1]",
                createdBy: person, lastUpdatedBy: person).save(flush:true, failOnError: true)

        def slideLayoutTwoColumns = new SlideLayout(columnsPerBlock: 2, rowsPerBlock: 15, numberOfBlocks: 48,
                title: "Two Column Layout", depositionPattern: "[4,4,2,2,1,1]",
                createdBy: person, lastUpdatedBy: person).save(flush: true, failOnError: true)

        def slide = new Slide(experimenter: person, antibody: primaryAB,
                dateOfStaining: new Date(), laserWavelength: 635, resultFile: resultFile, resultImage: null,
                layout: slideLayout, photoMultiplierTube: 4, protocol:  null,
                createdBy: person, lastUpdatedBy: person).save(flush:true, failOnError: true)

        def slideB = new Slide(experimenter: person, antibody: primaryAB,
                dateOfStaining: new Date(), laserWavelength: 635, resultFile: resultFile, resultImage: null,
                layout: slideLayout, photoMultiplierTube: 4, protocol:  null,
                createdBy: person, lastUpdatedBy: person).save(flush:true, failOnError:  true)

        def slideC = new Slide(experimenter: person, antibody: primaryAB2,
                dateOfStaining: new Date(), laserWavelength: 635, resultFile: resultFile, resultImage: null,
                layout: slideLayout, photoMultiplierTube: 4, protocol:  null,
                createdBy: person, lastUpdatedBy: person).save(flush:true, failOnError:  true)

        def slideD = new Slide(experimenter: person, antibody: primaryAB2,
                dateOfStaining: new Date(), laserWavelength: 635, resultFile: resultFile, resultImage: null,
                layout: slideLayout, photoMultiplierTube: 4, protocol:  null,
                createdBy: person, lastUpdatedBy: person).save(flush:true, failOnError:  true)

        def slideTwoColumns = new Slide(experimenter: person, antibody: primaryAB2,
                dateOfStaining: new Date(), laserWavelength: 635, resultFile: resultFileTwoColums, resultImage: null,
                layout: slideLayoutTwoColumns, photoMultiplierTube: 4, protocol:  null,
                createdBy: person, lastUpdatedBy: person).save(flush:true, failOnError:  true)

        /* slide layout    */

        def cellLine = new CellLine(name: "Co2", color: "#bb0000").save(flush:true, failOnError: true)
        def cellLineCo9 = new CellLine(name: "Co9", color: "#00bb00").save(flush:true, failOnError: true)
        def lysisBuffer = new LysisBuffer(name: "LB 5", concentration: 5, concentrationUnit: "mM", color: "#00aaaa").save(flush:true, failOnError: true)
        def lysisBuffer10 = new LysisBuffer(name: "LB 10", concentration: 10, concentrationUnit: "mM", color: "#0000bb").save(flush:true, failOnError: true)

        slideLayoutService.createSampleSpots(slideLayout)
        slideLayoutService.createSampleSpots(slideLayoutTwoColumns)

        def project = new Project(projectTitle: "Test project", projectDescription: "A project to test", createdBy: person, lastUpdatedBy: person).save(flush:true, failOnError: true)
    }

    def destroy = {
    }
}
