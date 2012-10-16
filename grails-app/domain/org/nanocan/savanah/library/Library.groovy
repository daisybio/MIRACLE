package org.nanocan.savanah.library

import org.nanocan.savanah.security.Person

class Library {

    String name
    String type

    Date dateCreated
    Date lastUpdated

    Person createdBy
    Person lastUpdatedBy

    static constraints = {
        name unique: true
        type inList: ["microRNA inhibitor", "microRNA mimics", "genome-wide", "druggable genome"]
    }

    String toString(){
        name
    }

    static mapping = {
        datasource "SAVANAH"
    }
}
