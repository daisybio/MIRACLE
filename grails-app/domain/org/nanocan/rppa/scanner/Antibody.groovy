package org.nanocan.rppa.scanner

class Antibody {

    String name
    Double concentration
    String concentrationUnit
    String comments

    static constraints = {
        name()
        concentration nullable:true
        concentrationUnit inList: ["nM", "mM", "pM", "µm"], nullable:true
        comments nullable: true
    }

    String toString()
    {
       if(concentration != null && concentrationUnit!= null)
        (name + " " + concentration.toString() + " " + concentrationUnit)

       else
        name
    }
}
