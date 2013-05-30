package org.nanocan.rppa.layout

class LysisBuffer implements Serializable{

    String name
    Double concentration
    String concentrationUnit
    String color

    static constraints = {
        concentration  min: new Double(0)
        concentrationUnit inList: ["mM", "nM", "pM" ,"µM"]
        color unique:  true, validator:  {val, obj -> val != "#ffffff"}, nullable: false, blank: false
        name unique: true, blank: false, nullable:  false
    }

    String toString()
    {
        (name + " " + concentration.toString() + " " + concentrationUnit)
    }
}
