package org.nanocan.rppa.project

import org.junit.*
import grails.test.mixin.*
import org.nanocan.project.Experiment
import org.nanocan.project.ExperimentController

@TestFor(ExperimentController)
@Mock(Experiment)
class ExperimentControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/experiment/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.experimentInstanceList.size() == 0
        assert model.experimentInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.experimentInstance != null
    }

    void testSave() {
        controller.save()

        assert model.experimentInstance != null
        assert view == '/experiment/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/experiment/show/1'
        assert controller.flash.message != null
        assert Experiment.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/experiment/list'


        populateValidParams(params)
        def experiment = new Experiment(params)

        assert experiment.save() != null

        params.id = experiment.id

        def model = controller.show()

        assert model.experimentInstance == experiment
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/experiment/list'


        populateValidParams(params)
        def experiment = new Experiment(params)

        assert experiment.save() != null

        params.id = experiment.id

        def model = controller.edit()

        assert model.experimentInstance == experiment
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/experiment/list'

        response.reset()


        populateValidParams(params)
        def experiment = new Experiment(params)

        assert experiment.save() != null

        // test invalid parameters in update
        params.id = experiment.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/experiment/edit"
        assert model.experimentInstance != null

        experiment.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/experiment/show/$experiment.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        experiment.clearErrors()

        populateValidParams(params)
        params.id = experiment.id
        params.version = -1
        controller.update()

        assert view == "/experiment/edit"
        assert model.experimentInstance != null
        assert model.experimentInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/experiment/list'

        response.reset()

        populateValidParams(params)
        def experiment = new Experiment(params)

        assert experiment.save() != null
        assert Experiment.count() == 1

        params.id = experiment.id

        controller.delete()

        assert Experiment.count() == 0
        assert Experiment.get(experiment.id) == null
        assert response.redirectedUrl == '/experiment/list'
    }
}
