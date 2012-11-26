package org.nanocan.rppa.layout

class Dilution implements Serializable{

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
