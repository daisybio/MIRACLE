package org.nanocan.rppa.layout

class Dilution {

    Double dilutionFactor
    String color

    static constraints = {
        color unique:  true, validator:  {val, obj -> val != "#ffffff"}, nullable: false, blank: false
    }

    String toString()
    {
        dilutionFactor
    }
}
