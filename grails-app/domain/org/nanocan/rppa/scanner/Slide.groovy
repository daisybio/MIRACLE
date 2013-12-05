package org.nanocan.rppa.scanner

import org.nanocan.file.ResultFile
import org.nanocan.file.ResultFileConfig
import org.nanocan.layout.SlideLayout
import org.nanocan.security.Person

class Slide{

    Date dateOfStaining

    Date dateCreated
    Date lastUpdated

    Person createdBy
    Person lastUpdatedBy

    String barcode
    Person experimenter
    int laserWavelength
    int photoMultiplierTube
    ResultFile resultFile
    ResultFile resultImage
    ResultFile protocol
    SlideLayout layout
    String title
    String comments
    ResultFileConfig lastConfig

    static hasMany = [spots: Spot, blockShifts: BlockShift]

    Antibody antibody

    static searchable = {
        antibody component: true
        experimenter component: true
    }

    static constraints = {
         lastConfig nullable: true
         barcode nullable: true
         laserWavelength min: 1, max: 1000
         protocol nullable: true
         resultImage nullable: true
         comments nullable: true
         title nullable: true
    }

    static mapping = {
        spots cascade: "all-delete-orphan"
        blockShifts cascade:  "all-delete-orphan"
    }

    String toString()
    {
        if(!title) (dateOfStaining.dateString + "_" + experimenter.toString() + "_" + antibody.toString())
        else title
    }
}
