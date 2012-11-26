package org.nanocan.savanah.attributes

class NumberOfCellsSeeded implements Serializable{

    String name
    int number
    String comments
    String color

    static constraints = {
        name nullable: true
        color unique:  true, validator:  {val, obj -> val != "#ffffff"}, nullable: false, blank: false
    }

    String toString()
    {
        (number)
    }

    static mapping = {
        datasource "SAVANAH"
    }
}
