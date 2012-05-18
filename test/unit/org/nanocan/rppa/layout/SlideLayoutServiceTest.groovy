package org.nanocan.rppa.layout

import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 18-05-12
 * Time: 09:42
 */


@TestFor(SlideLayoutService)
@TestMixin(DomainClassUnitTestMixin)

class SlideLayoutServiceTest extends GroovyTestCase{

    def slideLayoutService = new SlideLayoutService()

    def testParseDepositionPattern(){

        def intArray = slideLayoutService.parseDepositions("[4,4,2,2,1,1]")
        assertEquals([4, 4, 2, 2, 1, 1], intArray)
    }

    def testGetDepositionArray(){

        mockDomain(SlideLayout)

        def layout = new SlideLayout(depositionPattern: "[4,4,2,2,1,1]", columnsPerBlock: 2)

        def fullArray = slideLayoutService.getDepositionArray(layout)

        assertEquals([4, 4, 2, 2, 1, 1, 4, 4, 2, 2, 1, 1], fullArray)
    }
}
