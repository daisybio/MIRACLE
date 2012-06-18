package org.nanocan.rppa.scanner

import grails.plugin.spock.IntegrationSpec
import groovy.sql.Sql
import spock.lang.Stepwise

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 13-06-12
 * Time: 13:33
 */
class SpotImportServiceIntegrationSpec extends IntegrationSpec{

    def spotImportService
    def dataSourceUnproxied
    def grailsApplication

    def "create some dummy spots using groovy sql"()
    {
        setup:
        def sql = Sql.newInstance(dataSourceUnproxied)

        when: "create spots"

        sql.withBatch(200, 'insert into spot (version, bg, fg, block, col, diameter, flag, layout_spot_id, row, slide_id, x, y, signal) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)'){ stmt ->

            for(int block in 1..12)
            {
                for(int row in 1..72)
                {
                    for(int column in 1..6)
                    {
                        stmt.addBatch(0, 0, 0, block, column, 0, 0, null, row, slideInstance.id, 0, 0, 123)
                    }
                }
            }
        }

        sql.close()

        slideInstance.refresh()

        then: "database has 5184 spots"
        slideInstance.spots.size() == 5184

        where:
        slideInstance = Slide.get(1)

    }

    def "delete dummy spots using groovy sql"()
    {
        setup:
        def sql = Sql.newInstance(dataSourceUnproxied)

        when: "delete spots"
        sql.execute('delete from spot where slide_id = ?', slideInstance.id)
        sql.close()
        slideInstance.refresh()

        then: "database has 0 spots"
        slideInstance.spots.size() == 0

        where:
        slideInstance = Slide.get(1)
    }

    def "process a real result file to import spots using groovy SQL"(){

        setup:
        grailsApplication.config.rppa.jdbc.groovySql = true

        when: "process result file"
        spotImportService.processResultFile(slideInstance, sheetName, rfc)

        then: "slide has 5184 spots"

        slideInstance.spots.size() == 5184

        where:
        slideInstance = Slide.get(2)
        sheetName = "b-tubulin (ab7792) PMT0"
        rfc = ResultFileConfig.findByName("Default Config")
    }

    def "process a real result file to import spots using GORM and hibernate"(){

        setup:
        grailsApplication.config.rppa.jdbc.groovySql = false

        when: "process result file"
        spotImportService.processResultFile(slideInstance, sheetName, rfc)

        then: "slide has 5184 spots"

        slideInstance.spots.size() == 5184

        where:
        slideInstance = Slide.get(4)
        sheetName = "b-tubulin (ab7792) PMT0"
        rfc = ResultFileConfig.findByName("Default Config")
    }

    def "delete imported spots using service"(){

        when: "call service delete method"
        spotImportService.deleteSpots(slideInstanceId)

        then: "slide has 0 spots"
        Slide.get(slideInstanceId).spots.size() == 0

        where:
        slideInstanceId = 2
    }
}
