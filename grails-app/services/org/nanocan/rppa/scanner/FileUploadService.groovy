package org.nanocan.rppa.scanner

import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import liquibase.util.file.FilenameUtils
import org.apache.commons.io.FilenameUtils

/**
 * Service takes files out of the request and persists them in the appropriate fashion.
 */
class FileUploadService {

    def imageConvertService
    def grailsApplication

    /*
     * Creates the actual result file on the hard drive + a corresponding domain object
     */
    def createResultFile(def resultFile, String type)
    {
        if(!resultFile.empty) {

            def currentDate = new java.util.Date()
            long timestamp = currentDate.getTime()
            def filePath = grailsApplication.config.rppa.upload.directory + timestamp.toString() + "_" + resultFile.originalFilename

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

    /*
     * Creates tiles for the imagezoom plugin and does some image processing
     * depends on having graphicsmagick on the path
     */
    def zoomifyImage(String filePath) {
        Runtime rt = Runtime.getRuntime()

        //fomat path
        def formattedPath = makePathURLSafe(filePath)
        def exactOriginalPath = FilenameUtils.separatorsToSystem(filePath)
        def exactFormattedPath = FilenameUtils.separatorsToSystem(formattedPath)

        try{
        //put all color information into the red channel and rotate if height > width by -90 degrees.
        Process pr = rt.exec("cmd /c gm convert ${exactOriginalPath} -recolor \"1 1 1, 0 0 0, 0 0 0\" -rotate \"-90<\" -normalize ${exactFormattedPath}.jpg")
        pr.waitFor()

        def convertSettings = [:]
        convertSettings.numCPUCores = -1 //use all cores
        convertSettings.imgLib = "im4java-gm" // use graphics magick (alternative to im = imagemagick)

        //create tiles
        imageConvertService.createZoomifyImage("web-app/imagezoom", formattedPath + ".jpg", convertSettings)

        //clean up
        new File(formattedPath+".jpg").delete()

        } catch(FileNotFoundException e)
        {
            log.error "There was an error during image processing. A file was not found:" + e.getMessage()
            println e.stackTrace
        }
    }

    def makePathURLSafe(String filePath) {
        return FilenameUtils.getFullPath(filePath) + FilenameUtils.getBaseName(filePath).split("_")[0]
    }

    def getImagezoomFolder(def filePath) {
        return "imagezoom/" + FilenameUtils.removeExtension(FilenameUtils.getName(makePathURLSafe(filePath + "_colorized.jpg")))
    }

    /**
     * Distinguishes between different input types and triggers persistence
     * @param request
     * @param params
     * @return
     */
    def dealWithFileUploads(def request, def slideInstance)
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
}
