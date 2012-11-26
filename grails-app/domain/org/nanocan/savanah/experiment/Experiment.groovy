package org.nanocan.savanah.experiment

import org.nanocan.savanah.plates.Plate
import org.nanocan.savanah.security.Person
import org.nanocan.savanah.plates.PlateLayout

class Experiment implements Serializable{

    String name
    Date dateOfExperiment
    Person experimenter

    Date dateCreated
    Date lastUpdated

    Person createdBy
    Person lastUpdatedBy

    static hasMany = [plates: Plate, plateLayouts: PlateLayout]

    static constraints = {
        name unique: true
        experimenter()
        dateOfExperiment()
    }

    static mapping = {
        datasource 'SAVANAH'
    }

    String toString(){
        name
    }
}
