package org.nanocan.rppa.layout

class Treatment {

    String name
    String comments
    String color

    static constraints = {
        name unique: true, blank: false, nullable:  false
        color unique:  true, validator:  {val, obj -> val != "#ffffff"}, nullable: false, blank: false
    }

    String toString()
    {
        (name)
    }
}
