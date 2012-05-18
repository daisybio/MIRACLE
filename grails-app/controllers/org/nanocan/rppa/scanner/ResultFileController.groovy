package org.nanocan.rppa.scanner

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class ResultFileController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def show = {

        def resultFileInstance = ResultFile.get(params.id)

        def file = new File(resultFileInstance.filePath)

        if (file.exists()) {
            response.setContentType("application/octet-stream")
            response.setHeader("Content-disposition", "filename=${file.name}")
            response.outputStream << file.bytes
            return
        }

        else
        {
            flash.message = "file does not exist!"
            redirect(action: "show", id: params.id)
        }
    }

    def ajaxResultImageFinder = {
        ajaxFileFinder("Image")
    }

    def ajaxResultFileFinder = {
        ajaxFileFinder("Result")
    }

    def ajaxProtocolFinder = {
        ajaxFileFinder("Protocol")
    }

    def ajaxFileFinder = { type ->

        def resultFilesFound = ResultFile.withCriteria {
            and{
                eq 'fileType', type
                or{
                    ilike 'fileName', params.term + '%'
                    ilike 'filePath', params.term + '%'
                }
            }
        }

        def resultFileSelectList = []

        resultFilesFound.each{
            def resultMap = [:]
            resultMap.put("id", it.id)
            resultMap.put("label", (it.fileName + " (" + it.dateUploaded.toLocaleString()+ ")"))
            resultMap.put("value", it.fileName)
            resultMap.put("uploaded", it.dateUploaded)
            resultFileSelectList.add(resultMap)
        }

        render (resultFileSelectList as JSON)
    }

    def scaffold = true
}