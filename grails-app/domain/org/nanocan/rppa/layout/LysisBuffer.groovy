package org.nanocan.rppa.layout

class LysisBuffer {

    String name
    Double concentration
    String concentrationUnit
    String color

    static constraints = {
        concentrationUnit inList: ["mM", "nM", "pM" ,"µM"]
        color unique:  true, validator:  {val, obj -> val != "#ffffff"}, nullable: false, blank: false
        name unique: true, blank: false, nullable:  false
    }

    String toString()
    {
        (name + " " + concentration.toString() + " " + concentrationUnit)
    }
}
