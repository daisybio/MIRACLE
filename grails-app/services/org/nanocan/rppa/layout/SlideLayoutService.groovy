package org.nanocan.rppa.layout

import groovy.sql.Sql
import org.hibernate.util.ConfigHelper
import org.nanocan.rppa.project.Project

/**
 * This service handles additional operations regarding the slide layout
 */
class SlideLayoutService {

    //dependencies
    def progressService
    def dataSourceUnproxied
    def grailsApplication

    /**
     * Creates a full set of layout spots with respect to the number of columns, blocks and rows.
     * @param slideLayout
     * @return
     */
    def createSampleSpots(SlideLayout slideLayout) {

        //get config
        int batchSize = grailsApplication.config.rppa.jdbc.batchSize?:200
        boolean useGroovySql = grailsApplication.config.rppa.jdbc.groovySql.toString().toBoolean()

        log.debug "using groovy sql instead of GORM:" + useGroovySql

        def insertLoop = { stmt ->
            for (int block = 1; block <= slideLayout.numberOfBlocks; block++) {
                for (int col = 1; col <= slideLayout.columnsPerBlock; col++) {
                    for (int row = 1; row <= slideLayout.rowsPerBlock; row++) {
                        if (useGroovySql) stmt.addBatch(0, block, null, col, null, null, slideLayout.id, null, row, null, null)
                        else new LayoutSpot(block: block, col: col, row: row, layout: slideLayout).save()
                    }
                }
            }
        }

        if(useGroovySql){
            //create an sql instance for direct inserts via groovy sql
            def sql = Sql.newInstance(dataSourceUnproxied)

            sql.withBatch(batchSize, 'insert into layout_spot (version, block, cell_line_id, col, dilution_factor_id, inducer_id, layout_id, lysis_buffer_id, row, sample_id, spot_type_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)'){ stmt ->
                insertLoop(stmt)
            }
            //clean up
            sql.close()

            //refresh slide layout, because hibernate does not know about our changes
            slideLayout.refresh()
        }

        else insertLoop(null)

        return null
    }

    /**
     * The user can change spot properties in the web interface. This is a html table where each cell corresponds
     * to one layout spot. This method infers which property has been changed and updates the respective spot property.
     * @param spotProp
     * @param slideLayout
     * @return
     */
    def updateSpotProperties(params, spotProp, slideLayout, className) {
        //to calculate percentage for progress bar
        def numberOfSpots = params.keySet().size()
        def currentSpot = 0

        params.each {key, value ->
            if (value != "") {
                def spot = LayoutSpot.get(key as Long)

                if (value as Long == -1) spot.properties[spotProp] = null
                else spot.properties[spotProp] = grailsApplication.getArtefactByLogicalPropertyName("Domain", className).clazz.get(value as Long)

                spot.save()
            }

            progressService.setProgressBarValue("update${slideLayout}", (currentSpot / numberOfSpots * 100))
            currentSpot++
        }
    }
}


