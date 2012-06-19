package org.nanocan.rppa.rnai

import grails.converters.*
import grails.plugins.springsecurity.Secured;

@Secured(['ROLE_USER'])
class SampleController {

    def scaffold = true

    static navigation = [
            group: 'main',
            title: 'Sample Information'
    ]

    def ajaxSampleFinder = {
        def samplesFound = Sample.withCriteria {
            and{
                isEmpty("identifiers")

                or
                {
                    ilike 'name', params.term + '%'
                    ilike 'targetGene', params.term + '%'
                }
            }
        }

        def moreSamplesFound = Sample.withCriteria {
            or
            {
                ilike 'name', params.term + '%'
                ilike 'targetGene', params.term + '%'
                identifiers{
                   or
                   {
                     ilike 'name', params.term + '%'
                     ilike 'accessionNumber', params.term + '%'
                   }
                }
            }
        }

        samplesFound.addAll(moreSamplesFound)

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

        render "<script>alert('test');<script>"
    }
}
