package org.nanocan.rppa.scanner

import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

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
