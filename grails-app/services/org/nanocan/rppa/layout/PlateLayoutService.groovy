package org.nanocan.rppa.layout

import groovy.sql.Sql
import org.apache.commons.lang.StringUtils
import org.codehaus.groovy.grails.commons.ApplicationHolder

class PlateLayoutService {

    def grailsApplication
    def progressService
    def dataSourceUnproxied

    /**
     * Creates a full set of layout spots with respect to the number of columns, blocks and rows.
     * @param plateLayout
     * @return
     */
    def createWellLayouts(PlateLayout plateLayout) {

        //get config
        int batchSize = grailsApplication.config.rppa.jdbc.batchSize?:200
        boolean useGroovySql = grailsApplication.config.rppa.jdbc.groovySql.toString().toBoolean()

        log.debug "using groovy sql instead of GORM:" + useGroovySql

        def insertLoop = { stmt ->
            for (int col = 1; col <= plateLayout.cols; col++) {
                for (int row = 1; row <= plateLayout.rows; row++) {
                    if (useGroovySql) stmt.addBatch(0, col, plateLayout.id, row)
                    else new WellLayout(col: col, row: row, layout: plateLayout).save()
                }
            }
        }

        if(useGroovySql){
            //create an sql instance for direct inserts via groovy sql
            def sql = Sql.newInstance(dataSourceUnproxied)

            sql.withBatch(batchSize, 'insert into well_layout (version, col, plate_layout_id, row) values (?, ?, ?, ?)'){ stmt ->
                insertLoop(stmt)
            }
            //clean up
            sql.close()

            //refresh slide layout, because hibernate does not know about our changes
            plateLayout.refresh()
        }

        else insertLoop(null)

        return null
    }

    /**
     * The user can change well layout properties in the web interface. This is a html table where each cell corresponds
     * to one layout well. This method infers which property has been changed and updates the respective well property.
     * @param wellProp
     * @param plateLayout
     * @return
     */
    def updateWellProperties(params, wellProp, plateLayout){
        def numberOfWells = params.keySet().size()
        def currentWell = 0

        params.each {key, value ->
            if (value != "") {
                def well = WellLayout.get(key as Long)

                def classPrefix = "org.nanocan.rppa.layout."
                if(wellProp == "sample") classPrefix = "org.nanocan.rppa.rnai."
                if (value as Long == -1) well.properties[wellProp] = null
                else well.properties[wellProp] = grailsApplication.getDomainClass(classPrefix + wellProp.toString().capitalize()).clazz.get(value as Long)

                well.save()
            }

            progressService.setProgressBarValue("update${plateLayout}", (currentWell / numberOfWells * 100))
            currentWell++
        }
    }


    def deepClone(domainInstanceToClone){

        //Our target instance for the instance we want to clone
        def newDomainInstance = domainInstanceToClone.getClass().newInstance()

        //Returns a DefaultGrailsDomainClass (as interface GrailsDomainClass) for inspecting properties
        def domainClass = ApplicationHolder.application.getDomainClass(newDomainInstance.getClass().name)

        domainClass?.persistentProperties.each{prop ->
            if(prop.association){
                if(prop.owningSide){
                    //we have to deep clone owned associations
                    if(prop.oneToOne){
                        def newAssociationInstance = deepClone(domainInstanceToClone."${prop.name}")
                        newDomainInstance."${prop.name}" = newAssociationInstance
                    }
                    else{
                        domainInstanceToClone."${prop.name}".each{ associationInstance ->
                            def newAssociationInstance = deepClone(associationInstance)
                            newDomainInstance."addTo${StringUtils.capitalize(prop.name)}"(newAssociationInstance)
                        }
                    }
                }
                else{
                    if(!prop.bidirectional){
                        //If the association isn't owned or the owner, then we can just do a  shallow copy of the reference.
                        newDomainInstance."${prop.name}" = domainInstanceToClone."${prop.name}"
                    }
                }
            }
            else{
                //If the property isn't an association then simply copy the value
                newDomainInstance."${prop.name}" = domainInstanceToClone."${prop.name}"
            }
        }

        return newDomainInstance
    }
}
