package org.nanocan.rppa.scanner

import org.nanocan.rppa.layout.LayoutSpot
import org.nanocan.rppa.layout.SlideLayout
import org.hibernate.FetchMode
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.text.DecimalFormatSymbols
import java.text.DecimalFormat

class SlideService {

    def excelImportService
    def imageConvertService

    def dealWithFileUploads(def request, def params)
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

            if(!resultFile.empty) params["resultFile.id"] = createResultFile(resultFile, "Result").id
            else params.remove("resultFileInput")

            if(!resultImage.empty) params["resultImage.id"] = createResultFile(resultImage, "Image").id
            else params.remove("resultImageInput")

            if(!protocol.empty) params["protocol.id"] = createResultFile(protocol, "Protocol").id
            else params.remove("protocolInput")
        }

        return params
    }

    def grailsApplication

    def createResultFile(def resultFile, String type)
    {
        if(!resultFile.empty) {

            def currentDate = new java.util.Date()
            long timestamp = currentDate.getTime()
            def filePath = grailsApplication.config.rppa.upload.directory + timestamp.toString() + "_" + resultFile.originalFilename

            resultFile.transferTo( new File(filePath) )

            if(type == "Image")
            {
               imageConvertService.createZoomifyImage("upload", filePath, ['numCPUCores': -1])
            }

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

    def getSheets(def slideInstance)
    {
        def resultFile = slideInstance.resultFile

        ResultFileImporter importer = new ResultFileImporter()

        def fis = new FileInputStream(resultFile.filePath)

        importer.readFromStream(fis)
        def sheets = importer.getSheets()

        fis.close();

        return sheets
    }

    def sessionFactory
    def progressService
    def slideLayoutService

    def processResultFile(def slideInstance, String sheetName, ResultFileConfig rfc)
    {
        ResultFileImporter importer = new ResultFileImporter()

        def fis = new FileInputStream(slideInstance.resultFile.filePath)

        importer.readFromStream(fis)

        def spots = importer.getSpots(sheetName, rfc)

        fis.close()

        progressService.setProgressBarValue("excelimport", 0)
        def numberOfSpots = spots.size()
        def onePercent = (int) (numberOfSpots / 100)
        def nextStep = onePercent
        def currentPercent = 0

        def depositionList = slideLayoutService.parseDepositions(slideInstance.layout.depositionPattern)
        int deposLength = depositionList.size()

        int layId = slideInstance.layout.id

        /*we want to keep track of the last layout spot to reduce the database load */
        def lastBlock = -1
        def lastRow = -1
        def lastCol = -1

        def lastLayoutSpot

        spots.eachWithIndex{ obj, i ->

            //inserting many elements is very slow, we need to clean up hibernate cache regularly
            if(i % 50 == 0) cleanGorm()

            def newSpot = new Spot(obj)

            //match layout column to actual column
            int layoutColumn = (((obj.col as Integer)-1) / deposLength)
            //we don't want to start with zero
            layoutColumn++

            def layoutSpot

            if(lastBlock == obj.block && lastRow == obj.row && lastCol == layoutColumn){
                layoutSpot = lastLayoutSpot
            }

            else
            {
                lastBlock = obj.block
                lastRow = obj.row
                lastCol = layoutColumn

                layoutSpot = LayoutSpot.find {
                    ( layout.id == (layId as Long)
                            && block == (obj.block as Integer)
                            && row == (obj.row as Integer)
                            && col == layoutColumn )
                }

                if(layoutSpot == null){
                    progressService.setProgressBarValue("excelimport", 100)
                    return "Error: No layout information for spot ${newSpot}"

                }

                else{
                    lastLayoutSpot = layoutSpot
                }
            }

           layoutSpot.addToSpots(newSpot)
           slideInstance.addToSpots(newSpot)


            if(!newSpot.save())
            {
                log.error newSpot.errors
                printnl newSpot.errors
            }

            if(i == nextStep)
            {
                nextStep += onePercent
                progressService.setProgressBarValue("excelimport", currentPercent++)
            }
        }

        return null
    }

    def cleanGorm = {
        def session = sessionFactory.currentSession
        session.flush()
        session.clear()
        org.codehaus.groovy.grails.plugins.DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP.get().clear()
    }

    def exportToCSV(def slideInstance, def params) {

        def slideId = slideInstance.id

        def props = params.selectedProperties

        def spotCriteria = Spot.createCriteria().list{

            createAlias('layoutSpot', 'lspot')
            createAlias('slide', 'sl')
            createAlias('lspot.spotType', 'spotTp')
            createAlias('lspot.sample', 'spotSmple')
            createAlias('lspot.dilutionFactor', 'df')

            eq("sl.id", slideId )
            if(params.excludeBadFlags == "on") eq("flag", 0)
            if(params.excludeBadDiameter == "on") lt("diameter", 250)
            if(params.excludeBadSignal == "on") gt("signal", (0 as Double))

            projections {
                if("Block" in props) property("block")
                if("Column" in props) property("col")
                if("Row" in props) property("row")
                if("FG" in props) property("FG")
                if("BG" in props) property("BG")
                if("Signal" in props) property("signal")
                if("x" in props) property("x")
                if("y" in props) property("y")
                if("Diameter" in props) property("diameter")
                if("Flag" in props) property("flag")
                if("CellLine" in props) property("lspot.cellLine")
                if("LysisBuffer" in props) property("lspot.lysisBuffer")
                if("DilutionFactor" in props) property("df.dilutionFactor")
                if("Inducer" in props) property("lspot.inducer")
                if("SpotType" in props) property("spotTp.name")
                if("SpotClass" in props) property("spotTp.type")
                if("SampleName" in props) property("spotSmple.name")
                if("SampleType" in props) property("spotSmple.type")
                if("TargetGene" in props) property("spotSmple.targetGene")
            }
        }

        return fixDecimalSeparator(spotCriteria, params.decimalSeparator, params.decimalPrecision)
    }

    def includeBlockShifts(def spotCriteria, def slideInstance)
    {
        //get blockshiftMap
        def blockShiftMap = [:]

        BlockShift.findAllBySlide(slideInstance).each{
            blockShiftMap[it.blockNumber] = [it.horizontalShift, it.verticalShift]
        }

        spotCriteria.collect{ spot ->

           def array = []
           array.addAll(blockShiftMap[spot[0]]?:[0,0])
           array.addAll(spot)
           return(array)
        }
    }

    def fixDecimalSeparator(def spotCriteria, def decimalSeparator, def numberOfDecimals)
    {
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(decimalSeparator as char);
        df.setDecimalFormatSymbols(symbols);
        df.setGroupingUsed(false)

        spotCriteria.each {
            it.eachWithIndex{field, i ->
                if(field.toString().isDouble() && !field.toString().isInteger()) it[i] = df.format(field.round(Integer.parseInt(numberOfDecimals)))
            }
        }

        return spotCriteria
    }
}
