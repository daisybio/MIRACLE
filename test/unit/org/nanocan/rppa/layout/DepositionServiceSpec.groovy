package org.nanocan.rppa.layout

import spock.lang.Specification

class DepositionServiceSpec extends Specification{

    def depositionService

    def "parse a deposition pattern"(){

        when:
        def intArray = depositionService.parseDepositions(depositionPattern)

        then:
        intArray == [4, 4, 2, 2, 1, 1]

        where:
        depositionPattern = "[4,4,2,2,1,1]"
    }

    def "get a deposition array for a 2 columns per block layout"(){

        given:
        mockDomain(SlideLayout)
        def layout = new SlideLayout(depositionPattern: "[4,4,2,2,1,1]", columnsPerBlock: 2)

        when:
        def fullArray = depositionService.getDepositionArray(layout)

        then:
        fullArray == [4, 4, 2, 2, 1, 1, 4, 4, 2, 2, 1, 1]

        where:
        depositionPattern = "[4,4,2,2,1,1]"
    }
}
