package org.nanocan.savanah.plates

import java.io.Serializable;

import org.nanocan.savanah.library.Library

class Plate implements Serializable{

    PlateType plateType
    String format
    String family
    Plate parentPlate
    Library library
    Plate libraryPlate
    String barcode
    String name
    PlateLayout layout

    int cols
    int rows

    static mapping = {
        datasource "SAVANAH"
    }

    static constraints = {
        barcode unique:  true
        name nullable: true, blank: false, unique: true
        plateType nullable: true
        parentPlate nullable: true
        libraryPlate nullable: true
        library nullable: true
        family inList: ["library", "supermaster", "master", "mother", "daughter"]
        format inList: ["96-well", "384-well"], blank: false, editable: false
    }

    String toString(){
        name?:(id + "(" + family + ")")
    }
}
