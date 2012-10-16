package org.nanocan.savanah.experiment

import org.nanocan.savanah.security.Person

class Project {

    String name

    Date dateCreated
    Date lastUpdated

    Person createdBy
    Person lastUpdatedBy

    static hasMany = [experiments:Experiment]

    static constraints = {
        name unique: true
    }

    static mapping = {
        datasource 'SAVANAH'
    }

    String toString(){
        name
    }
}
