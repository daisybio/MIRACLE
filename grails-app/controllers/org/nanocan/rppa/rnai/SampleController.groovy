package org.nanocan.rppa.rnai

import grails.converters.*;

class SampleController {

    def scaffold = true

    static navigation = [
            group: 'main',
            title: 'Sample Information'
    ]

    def ajaxSampleFinder = {
        def samplesFound = Sample.withCriteria {
            or{
                ilike 'name', params.term + '%'
                ilike 'targetGene', params.term + '%'
            }
        }

        def sampleSelectList = []

        samplesFound.each{
            def resultMap = [:]
            resultMap.put("id", it.id)
            resultMap.put("label", it.name)
            resultMap.put("value", it.name)
            resultMap.put("colour", it.color)
            sampleSelectList.add(resultMap)
        }

        render (sampleSelectList as JSON)
    }

    def legendSampleSelected = {

        println "legendaction"
        println params

        render "<script>alert('test');<script>"
    }
}
