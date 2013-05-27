package org.nanocan.rppa.project

import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.rppa.scanner.Slide
import org.nanocan.rppa.project.Experiment
import org.nanocan.rppa.layout.PlateLayout

class ExperimentService {

    def findExperiment(doInstance) {
        return Experiment.where {
            if(doInstance instanceof SlideLayout) {
                layouts
                {
                    id == doInstance.id
                }
            }
            else if(doInstance instanceof Slide)
            {
                slides
                {
                    id == doInstance.id
                }
            }
            else if(doInstance instanceof PlateLayout)
            {
                plateLayouts
                {
                    id == doInstance.id
                }
            }
        }.list()

    }

    def addToExperiment(doInstance, experimentsSelected) {
        experimentsSelected.each {
            def experiment
            if(it instanceof Experiment) experiment = it
            else experiment = Experiment.get(it as Long)

            if(doInstance instanceof SlideLayout) experiment.addToLayouts(doInstance).save(flush: true)
            else if(doInstance instanceof Slide) experiment.addToSlides(doInstance).save(flush: true)
            else if(doInstance instanceof PlateLayout) experiment.addToPlateLayouts(doInstance).save(flush:true)
        }
    }

    def removeFromExperiment(doInstance, experimentsToRemoveFrom)
    {
        experimentsToRemoveFrom.each {
            def experiment
            if(it instanceof Experiment) experiment = it
            else experiment = Experiment.get(it as Long)

            if(doInstance instanceof SlideLayout) experiment.removeFromLayouts(doInstance).save(flush: true)
            else if(doInstance instanceof Slide) experiment.removeFromSlides(doInstance).save(flush: true)
            else if(doInstance instanceof PlateLayout) experiment.removeFromPlateLayouts(doInstance).save(flush: true)
        }
    }

    def updateExperiments(doInstance, experimentsSelected) {

        def experiments = findExperiment(doInstance)

        if(experimentsSelected == null && experiments == null) return

        else if(experimentsSelected == null && experiments.size() > 0)
        {
            removeFromExperiment(doInstance, experiments)
        }

        else if(experimentsSelected != null && experiments.size() == 0)
        {
            addToExperiment(doInstance, experimentsSelected)
        }

        else{
            experimentsSelected = experimentsSelected.collect{Experiment.get(it as Long)}

            def experimentsToRemoveFrom = experiments
            experimentsToRemoveFrom.removeAll(experimentsSelected)

            def experimentsToAddTo = experimentsSelected
            experimentsToAddTo.removeAll(experiments)

            if(experimentsToAddTo.size() > 0) addToExperiment(doInstance, experimentsToAddTo)
            if(experimentsToRemoveFrom.size() >0) removeFromExperiment(doInstance, experimentsToRemoveFrom)
        }
        return
    }
}
