import org.nanocan.rppa.scanner.Experimenter
import org.nanocan.rppa.scanner.Antibody
import org.nanocan.rppa.scanner.ResultFileConfig

import org.nanocan.rppa.scanner.Slide
import org.nanocan.rppa.scanner.ResultFile
import sebastian.hohns.imagezoom.imageops.ImagemagickOperations
import org.im4java.core.Info
import rppa.layout.SlideLayout
import rppa.layout.CellLine
import rppa.layout.LysisBuffer

class BootStrap {

    def slideLayoutService

    def init = { servletContext ->

        /* slide staining */
        def experimenter = new Experimenter(firstName: "Markus", lastName: "List").save()

        def primaryAB = new Antibody(name: "p53").save()
        def primaryAB2 = new Antibody(name: "GAPDH").save()

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

        def resultFile = new ResultFile(fileName: "2012-03-28 b-tubulin abcam abnova original.xls", filePath:  fileName, dateUploaded: new Date()).save()
        def resultImage = new ResultFile(fileName: "slide1.png", filePath:  fileNamePicture, dateUploaded:  new Date()).save()

        def slideLayout = new SlideLayout(columnsPerBlock: 2, rowsPerBlock: 18, numberOfBlocks: 48,
                title: "Default Layout", depositionPattern: "[4,4,2,2,1,1]").save()

        def slide = new Slide(experimenter: experimenter, antibody: primaryAB,
                dateOfStaining: new Date(), laserWavelength: 635, resultFile: resultFile, resultImage: resultImage,
                layout: slideLayout).save()

        //ResultFileImporter importer = new ResultFileImporter()

        //importer.readFromFile(fileName)

        //def spots = importer.getSpots("b-tubulin (ab7792) PMT0", resultFileConfig)

        //spots.each{ println it }

        /* slide layout */

        def cellLine = new CellLine(name: "Co2", color: "#bb0000").save()
        def cellLineCo9 = new CellLine(name: "Co9", color: "#00bb00").save()
        def lysisBuffer = new LysisBuffer(name: "LB", concentration: 5, concentrationUnit: "mM", color: "#00aaaa").save()
        def lysisBuffer10 = new LysisBuffer(name: "LB", concentration: 10, concentrationUnit: "mM", color: "#0000bb").save()

        slideLayoutService.createSampleSpots(slideLayout)
    }
    def destroy = {
    }
}
