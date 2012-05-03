package org.nanocan.rppa.scanner

class Experimenter {

    String firstName
    String lastName

    String toString()
    {
        (firstName + " " + lastName)
    }

    static constraints = {
    }
}
