package org.nanocan.rppa.layout

import grails.plugin.spock.IntegrationSpec
import org.codehaus.groovy.grails.commons.cfg.ConfigurationHelper
import org.nanocan.layout.SlideLayout

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 15-06-12
 * Time: 10:55
 */
class SlideLayoutServiceIntegrationSpec extends IntegrationSpec{

    def slideLayoutService
    def grailsApplication

    def "create slide layout spots using groovy sql"(){
        setup:
        grailsApplication.config.rppa.jdbc.groovySql = true

        when:
        slideLayoutService.createSampleSpots(slideLayoutInstance)

        then:
        slideLayoutInstance.sampleSpots.size() == 2592

        where:
        slideLayoutInstance = new SlideLayout(columnsPerBlock: 6, rowsPerBlock: 12, numberOfBlocks: 36,
                title: "Test Layout", depositionPattern: "[4,4,2,2,1,1]").save(flush:true, failOnError: true)
    }

    def "create slide layout spots using GORM and hibernate"(){
        setup:
        grailsApplication.config.rppa.jdbc.groovySql = false

        when:
        slideLayoutService.createSampleSpots(slideLayoutInstance)

        then:
        slideLayoutInstance.sampleSpots.size() == 2592

        where:
        slideLayoutInstance = new SlideLayout(columnsPerBlock: 6, rowsPerBlock: 12, numberOfBlocks: 36,
                title: "New Test Layout", depositionPattern: "[4,4,2,2,1,1]").save(flush:true, failOnError: true)
    }
}
