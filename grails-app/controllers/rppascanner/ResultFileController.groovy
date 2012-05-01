package rppascanner

import org.springframework.dao.DataIntegrityViolationException

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

    def scaffold = true
}