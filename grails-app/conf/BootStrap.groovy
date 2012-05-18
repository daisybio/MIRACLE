import org.nanocan.rppa.scanner.Experimenter
import org.nanocan.rppa.scanner.Antibody
import org.nanocan.rppa.scanner.ResultFileConfig

import org.nanocan.rppa.scanner.Slide
import org.nanocan.rppa.scanner.ResultFile
import sebastian.hohns.imagezoom.imageops.ImagemagickOperations
import org.im4java.core.Info
import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.rppa.layout.CellLine
import org.nanocan.rppa.layout.LysisBuffer
import org.nanocan.rppa.layout.Dilution

import org.nanocan.rppa.layout.SpotType
import org.nanocan.rppa.layout.Inducer
import org.nanocan.rppa.rnai.Sample

class BootStrap {

    def slideLayoutService

    def init = { servletContext ->

        /* slide staining */
        /*def experimenter = new Experimenter(firstName: "Markus", lastName: "List").save()

        def primaryAB = new Antibody(name: "p53", concentration: 5, concentrationUnit: "mM").save()
        def primaryAB2 = new Antibody(name: "GAPDH", concentration: 5, concentrationUnit: "mM").save()

        def dilution = new Dilution(dilutionFactor:  1.0, color: "#e0e0e0").save(flush:true)
        def dilutionA = new Dilution(dilutionFactor:  0.5, color: "#aa0000").save(flush:true)
        def dilutionB = new Dilution(dilutionFactor:  0.25, color: "#00bb00").save(flush:true)
        def dilutionC = new Dilution(dilutionFactor:  0.125, color: "#0000aa").save(flush:true)

        new SpotType(name: "LB", type: "Buffer", color:  "#550000").save()
        new SpotType(name:  "IgG10", type: "Control", color:  "#000055").save()
        new SpotType(name: "IgG100", type: "Control", color:  "#0000aa").save()
        new SpotType(name: "Sample", type: "Sample", color:  "#005500").save()

        new Inducer(name: "Dox .5", concentration: 0.5, concentrationUnit: "mM", color: "#aa0000").save()
        new Inducer(name: "Dox .1", concentration: 0.1, concentrationUnit: "mM", color: "#550000").save()

        new Sample(name: "siRNA1", type: "siRNA", targetGene: "Gene1", color: "#a0a000").save()
        new Sample(name: "siRNA2", type: "siRNA", targetGene: "Gene2", color: "#00a0a0").save()
        new Sample(name: "siRNA3", type:  "siRNA", targetGene: "Gene3", color: "#a000a0").save()

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
                skipLines: 26
        ).save()

        String fileName = "C:\\temp\\2012-03-28 b-tubulin abcam abnova original.xls"
        String fileNamePicture = "C:\\temp\\slide1.png"

        def io = new ImagemagickOperations("tif", false)
        println io.determineImageDimension(fileNamePicture)

        Info info = new Info(fileNamePicture);
        String size = info.getProperty("Geometry");
        println size.substring(0, size.indexOf("+"));

        def resultFile = new ResultFile(fileType: "Result", fileName: "2012-03-28 b-tubulin abcam abnova original.xls", filePath:  fileName, dateUploaded: new Date()).save()
        def resultImage = new ResultFile(fileType: "Image", fileName: "slide1.png", filePath:  fileNamePicture, dateUploaded:  new Date()).save()
        def resultProtocol = new ResultFile(fileType: "Protocol", fileName: "protocol.doc", filePath:  fileNamePicture, dateUploaded:  new Date()).save()

        def slideLayout = new SlideLayout(columnsPerBlock: 2, rowsPerBlock: 18, numberOfBlocks: 48,
                title: "Default Layout", depositionPattern: "[4,4,2,2,1,1]").save()

        def slide = new Slide(experimenter: experimenter, antibody: primaryAB,
                dateOfStaining: new Date(), laserWavelength: 635, resultFile: resultFile, resultImage: resultImage,
                layout: slideLayout, photoMultiplierTube: 4, protocol:  resultProtocol).save()

        //ResultFileImporter importer = new ResultFileImporter()

        //importer.readFromFile(fileName)

        //def spots = importer.getSpots("b-tubulin (ab7792) PMT0", resultFileConfig)

        //spots.each{ println it }

        /* slide layout

        def cellLine = new CellLine(name: "Co2", color: "#bb0000").save()
        def cellLineCo9 = new CellLine(name: "Co9", color: "#00bb00").save()
        def lysisBuffer = new LysisBuffer(name: "LB", concentration: 5, concentrationUnit: "mM", color: "#00aaaa").save()
        def lysisBuffer10 = new LysisBuffer(name: "LB", concentration: 10, concentrationUnit: "mM", color: "#0000bb").save()

        slideLayoutService.createSampleSpots(slideLayout)      */

    }
    def destroy = {
    }
}
