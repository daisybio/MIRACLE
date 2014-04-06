/*
 * Copyright (C) 2014
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:			http://www.nanocan.org/miracle/
 * ###########################################################################
 *	
 *	This file is part of MIRACLE.
 *
 *  MIRACLE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
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
