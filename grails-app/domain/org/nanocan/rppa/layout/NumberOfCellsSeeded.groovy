package org.nanocan.rppa.layout

class NumberOfCellsSeeded {

    String name
    String color

    static constraints = {
        color unique:  true, validator:  {val, obj -> val != "#ffffff"}, nullable: false, blank: false
    }

    String toString()
    {
        name
    }
}
