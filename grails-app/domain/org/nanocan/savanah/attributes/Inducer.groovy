package org.nanocan.savanah.attributes

class Inducer implements Serializable{

    String name
    double concentration
    String concentrationUnit
    String color

    static constraints = {
        name unique: true, blank: false, nullable:  false
        concentration()
        concentrationUnit inList: ["mM", "nM", "pM" ,"µM"]
        color unique:  true, validator:  {val, obj -> val != "#ffffff"}, nullable: false, blank: false
    }

    String toString()
    {
        (name + " " + concentration.toString() + " " + concentrationUnit)
    }

    static mapping = {
        datasource "SAVANAH"
    }

}
