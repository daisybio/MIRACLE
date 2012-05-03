package org.nanocan.rppa.layout

class LysisBuffer {

    String name
    Double concentration
    String concentrationUnit
    String color

    static constraints = {
        concentrationUnit inList: ["mM", "nM", "pM" ,"µM"]
        color unique: true
        name unique: true
    }

    String toString()
    {
        (name + " " + concentration.toString() + " " + concentrationUnit)
    }
}
