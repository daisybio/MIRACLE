package org.nanocan.savanah.plates

class PlateType {

    String name
    String vendor
    String description

    static constraints = {
    }

    String toString()
    {
        name
    }

    static mapping = {
        datasource "SAVANAH"
    }
}
