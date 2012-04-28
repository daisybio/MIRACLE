package rppascanner

import org.springframework.dao.DataIntegrityViolationException

class ResultFileController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [resultFileInstanceList: ResultFile.list(params), resultFileInstanceTotal: ResultFile.count()]
    }

    def create() {
        [resultFileInstance: new ResultFile(params)]
    }

    def save() {
        def resultFileInstance = new ResultFile(params)
        if (!resultFileInstance.save(flush: true)) {
            render(view: "create", model: [resultFileInstance: resultFileInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'resultFile.label', default: 'ResultFile'), resultFileInstance.id])
        redirect(action: "show", id: resultFileInstance.id)
    }

    def show() {
        def resultFileInstance = ResultFile.get(params.id)
        if (!resultFileInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'resultFile.label', default: 'ResultFile'), params.id])
            redirect(action: "list")
            return
        }

        [resultFileInstance: resultFileInstance]
    }

    def edit() {
        def resultFileInstance = ResultFile.get(params.id)
        if (!resultFileInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'resultFile.label', default: 'ResultFile'), params.id])
            redirect(action: "list")
            return
        }

        [resultFileInstance: resultFileInstance]
    }

    def update() {
        def resultFileInstance = ResultFile.get(params.id)
        if (!resultFileInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'resultFile.label', default: 'ResultFile'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (resultFileInstance.version > version) {
                resultFileInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'resultFile.label', default: 'ResultFile')] as Object[],
                        "Another user has updated this ResultFile while you were editing")
                render(view: "edit", model: [resultFileInstance: resultFileInstance])
                return
            }
        }

        resultFileInstance.properties = params

        if (!resultFileInstance.save(flush: true)) {
            render(view: "edit", model: [resultFileInstance: resultFileInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'resultFile.label', default: 'ResultFile'), resultFileInstance.id])
        redirect(action: "show", id: resultFileInstance.id)
    }

    def delete() {
        def resultFileInstance = ResultFile.get(params.id)
        if (!resultFileInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'resultFile.label', default: 'ResultFile'), params.id])
            redirect(action: "list")
            return
        }

        try {
            resultFileInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'resultFile.label', default: 'ResultFile'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'resultFile.label', default: 'ResultFile'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
