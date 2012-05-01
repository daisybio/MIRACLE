import rppascanner.Experimenter
import rppascanner.Antibody
import rppascanner.ResultFileConfig
import rppascanner.ResultFileImporter
import rppascanner.Slide
import rppascanner.ResultFile

class BootStrap {

    def init = { servletContext ->

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

        def resultFile = new ResultFile(fileName: "2012-03-28 b-tubulin abcam abnova original.xls", filePath:  fileName, dateUploaded: new Date()).save()
        def resultImage = new ResultFile(fileName: "slide1.png", filePath:  fileNamePicture, dateUploaded:  new Date()).save()

        def slide = new Slide(experimenter: experimenter, antibody: primaryAB,
                dateOfStaining: new Date(), laserWavelength: 635, resultFile: resultFile, resultImage: resultImage ).save()

        ResultFileImporter importer = new ResultFileImporter()

        importer.readFromFile(fileName)

        def spots = importer.getSpots("b-tubulin (ab7792) PMT0", resultFileConfig)

        spots.each{ println it }

    }
    def destroy = {
    }
}
