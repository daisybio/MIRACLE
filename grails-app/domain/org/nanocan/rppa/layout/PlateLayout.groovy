package org.nanocan.rppa.layout

class PlateLayout implements Serializable{

    String name
    String format

    int cols
    int rows

    Boolean controlPlate = false

    static constraints = {
        controlPlate nullable: true
        name blank: false, unique:  true
        format inList: ["96-well", "384-well"], blank: false, editable: false
    }

    static hasMany = [wells: WellLayout]
    SortedSet wells

    def beforeInsert = {
        if(format == "96-well")
        {
            cols = 12
            rows = 8
        }
        else if(format == "384-well")
        {
            cols = 24
            rows = 16
        }
    }

    def beforeUpdate = {
        beforeInsert()
    }

    String toString()
    {
        name
    }
}
