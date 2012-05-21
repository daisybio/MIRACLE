package org.nanocan.rppa.rnai

class Identifier {

    String name
    String accessionNumber
    String type

    static belongsTo = [sample: Sample]

    static constraints = {
        name()
        type inList: ["miRBase", "NCBI"]
    }
}
