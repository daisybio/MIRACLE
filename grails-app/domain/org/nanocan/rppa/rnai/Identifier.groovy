package org.nanocan.rppa.rnai

class Identifier implements Serializable{

    String name
    String accessionNumber
    String type

    static belongsTo = [sample: Sample]

    static constraints = {
        name()
        type inList: ["miRBase", "NCBI"]
    }
}
