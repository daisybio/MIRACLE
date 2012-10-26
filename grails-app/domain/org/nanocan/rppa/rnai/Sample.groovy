package org.nanocan.rppa.rnai

class Sample {

    String name
    String type
    String targetGene
    String color

    static hasMany = [identifiers: Identifier]

    static constraints = {
        name unique: true
        type inList: ["siRNA", "miRNA inhibitor", "miRNA mimic"]
        targetGene()
        color unique:  false, validator:  {val, obj -> (val != "#ffffff" && val != "#e0e0e0")}, nullable: false
    }

    String toString()
    {
        name
    }
}
