package org.nanocan.rppa.layout

class CellLine {

    String name
    String color

    static constraints = {
        name unique:  true
        color unique:  true
    }

    String toString(){
        name
    }
}
