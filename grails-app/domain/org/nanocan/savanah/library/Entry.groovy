package org.nanocan.savanah.library

import org.nanocan.savanah.plates.Plate

class Entry implements Serializable {

    Library library
    Plate libraryPlate
    int wellNum
    int columnNum
    int rowNum

    String accession
    String accessionType

    String sample

    static mapping = {
        datasource "SAVANAH"
        id composite: ["library", "libraryPlate", "wellNum"]
        sample column: "sample_id"
        accessionType column: "accession_type"
        wellNum column:  "well_id"
        columnNum column:  "well_column"
        rowNum column:  "well_row"
    }

    static constraints = {
        accession nullable: true
        accessionType inList: ["miRBase"], nullable: true
        sample nullable: true
    }

    String toString(){
        sample
    }
}

