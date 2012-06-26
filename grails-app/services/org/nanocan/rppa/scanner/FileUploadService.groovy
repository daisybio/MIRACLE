package org.nanocan.rppa.scanner

import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.apache.commons.io.FileUtils
import org.im4java.core.IMOperation
import org.im4java.core.RecolorGMOperation
import org.im4java.core.GraphicsMagickCmd
import org.im4java.core.GMOperation
import liquibase.util.file.FilenameUtils

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

    def zoomifyImage(String filePath) {
        Runtime rt = Runtime.getRuntime()
        def exactPath = FilenameUtils.separatorsToSystem(filePath)

        Process pr = rt.exec("cmd /c gm convert ${exactPath} -recolor \"1 1 1, 0 0 0, 0 0 0\" -rotate \"-90<\" -normalize ${exactPath}_colorized.jpg")
        pr.waitFor()

        def convertSettings = [:]
        convertSettings.numCPUCores = -1
        convertSettings.imgLib = "im4java-gm"

        imageConvertService.createZoomifyImage("web-app/imagezoom", filePath + "_colorized.jpg", convertSettings)
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
