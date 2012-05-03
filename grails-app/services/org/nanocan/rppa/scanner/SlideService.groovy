package org.nanocan.rppa.scanner

class SlideService {

    def excelImportService
    def imageConvertService

    def createResultFile(def resultFile, boolean image)
    {
        if(!resultFile.empty) {

            def currentDate = new java.util.Date()
            long timestamp = currentDate.getTime()
            def filePath = "upload/" + timestamp.toString() + "_" + resultFile.originalFilename

            resultFile.transferTo( new File(filePath) )

            if(image)
            {
               println imageConvertService.createZoomifyImage("upload", filePath, ['numCPUCores': -1])
            }

            def newResultFile = new ResultFile(fileName: (resultFile.originalFilename as String), filePath: filePath, dateUploaded:  currentDate as Date)

            if(newResultFile.save(flush: true))
            {
                log.info "save of file ${filePath} successfull"
                return newResultFile
            }

            else
            {
                log.warn "could not save file to ${filePath}."
                return null
            }
        }
    }

    def getSheets(def slideInstance)
    {
        def resultFile = slideInstance.resultFile

        ResultFileImporter importer = new ResultFileImporter()
        importer.readFromFile(resultFile.filePath)

        importer.getSheets()
    }


    def processResultFile(def slideInstance, String sheetName, ResultFileConfig rfc)
    {
        ResultFileImporter importer = new ResultFileImporter()
        importer.readFromFile(slideInstance.resultFile.filePath)

        importer.getSpots(sheetName, rfc).each{
            slideInstance.addToSpots(new Spot(it))
        }
    }
}
