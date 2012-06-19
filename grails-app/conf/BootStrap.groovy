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

class BootStrap {

    def slideLayoutService

    def init = { servletContext ->

        switch (GrailsUtil.environment) {
            case "development":

                initUserbase()
                initSampleData()
                break

            case "test":
                initSampleData()
                break
        }

    }

    private void initUserbase(){

        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true, failOnError: true)
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)

        def testUser = new Person(username: 'user', enabled: true, password: 'password')
        testUser.save(flush: true, failOnError: true)
        PersonRole.create testUser, userRole, true

        def adminUser = new Person(username: 'admin', enabled: true, password: 'password')
        adminUser.save(flush: true, failOnError: true)
        PersonRole.create adminUser, adminRole, true
        PersonRole.create adminUser, userRole, true

        assert Person.count() == 2
        assert Role.count() == 2
        assert PersonRole.count() == 3
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

        def resultFile = new ResultFile(fileType: "Result", fileName: "2012-03-28 b-tubulin abcam abnova original.xls",
                filePath:  fileName, dateUploaded: new Date()).save(flush: true, failOnError: true)

        def person = Person.get(1)

        def slideLayout = new SlideLayout(columnsPerBlock: 1, rowsPerBlock: 72, numberOfBlocks: 12,
                title: "Default Layout", depositionPattern: "[4,4,2,2,1,1]",
                createdBy: person, lastUpdatedBy: person).save(flush:true, failOnError: true)

        def slide = new Slide(experimenter: person, antibody: primaryAB,
                dateOfStaining: new Date(), laserWavelength: 635, resultFile: resultFile, resultImage: null,
                layout: slideLayout, photoMultiplierTube: 4, protocol:  null,
                createdBy: person, lastUpdatedBy: person).save(flush:true, failOnError: true)

        def slideB = new Slide(experimenter: experimenter, antibody: primaryAB,
                dateOfStaining: new Date(), laserWavelength: 635, resultFile: resultFile, resultImage: null,
                layout: slideLayout, photoMultiplierTube: 4, protocol:  null).save(flush:true, failOnError:  true)

        def slideC = new Slide(experimenter: experimenter, antibody: primaryAB2,
                dateOfStaining: new Date(), laserWavelength: 635, resultFile: resultFile, resultImage: null,
                layout: slideLayout, photoMultiplierTube: 4, protocol:  null).save(flush:true, failOnError:  true)

        def slideD = new Slide(experimenter: experimenter, antibody: primaryAB2,
                dateOfStaining: new Date(), laserWavelength: 635, resultFile: resultFile, resultImage: null,
                layout: slideLayout, photoMultiplierTube: 4, protocol:  null).save(flush:true, failOnError:  true)

        //ResultFileImporter importer = new ResultFileImporter()

        //importer.readFromFile(fileName)

        //def spots = importer.getSpots("b-tubulin (ab7792) PMT0", resultFileConfig)

        //spots.each{ println it }

        /* slide layout    */

        def cellLine = new CellLine(name: "Co2", color: "#bb0000").save(flush:true, failOnError: true)
        def cellLineCo9 = new CellLine(name: "Co9", color: "#00bb00").save(flush:true, failOnError: true)
        def lysisBuffer = new LysisBuffer(name: "LB 5", concentration: 5, concentrationUnit: "mM", color: "#00aaaa").save(flush:true, failOnError: true)
        def lysisBuffer10 = new LysisBuffer(name: "LB 10", concentration: 10, concentrationUnit: "mM", color: "#0000bb").save(flush:true, failOnError: true)
        slideLayoutService.createSampleSpots(slideLayout)
    }

    def destroy = {
    }
}
