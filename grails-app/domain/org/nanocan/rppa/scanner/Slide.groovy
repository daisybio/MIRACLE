package org.nanocan.rppa.scanner

import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.rppa.security.Person

class Slide{

    Date dateOfStaining

    Date dateCreated
    Date lastUpdated

    Person createdBy
    Person lastUpdatedBy

    Person experimenter
    int laserWavelength
    int photoMultiplierTube
    ResultFile resultFile
    ResultFile resultImage
    ResultFile protocol
    SlideLayout layout
    String comments

    static hasMany = [spots: Spot, blockShifts: BlockShift]

    Antibody antibody

    static constraints = {
         laserWavelength min: 1, max: 1000
         protocol nullable: true
         resultImage nullable: true
         comments nullable: true
    }

    static mapping = {
        spots cascade: "all-delete-orphan"
        blockShifts cascade:  "all-delete-orphan"
    }

    String toString()
    {
        (dateOfStaining.dateString + "_" + experimenter.toString() + "_" + antibody.toString())
    }
}
