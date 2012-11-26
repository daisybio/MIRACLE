package org.nanocan.savanah.attributes

class CellLine implements Serializable{

    String name
    String color

    static constraints = {
        name unique:  true , nullable: false
        color unique:  true, validator:  {val, obj -> val != "#ffffff"}, nullable: false, blank: false
    }

    String toString(){
        name
    }

    static mapping = {
        datasource "SAVANAH"
    }

}
