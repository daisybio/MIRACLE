package org.nanocan.rppa.scanner

class Antibody {

    String name
    double concentration
    String concentrationUnit

    static constraints = {
        name()
        concentration()
        concentrationUnit inList: ["nM", "mM", "pM", "µm"]
    }

    String toString()
    {
        (name + " " + concentration.toString() + " " + concentrationUnit)
    }
}
