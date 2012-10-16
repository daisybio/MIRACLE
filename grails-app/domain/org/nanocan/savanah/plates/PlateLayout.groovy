package org.nanocan.savanah.plates

class PlateLayout {

    String name
    String format

    int cols
    int rows

    static constraints = {
        name blank: false, unique:  true
        format inList: ["96-well", "384-well"], blank: false, editable: false
    }

    static hasMany = [wells: WellLayout]
    SortedSet wells

    String toString()
    {
        name
    }

    static mapping = {
        datasource "SAVANAH"
    }
}
