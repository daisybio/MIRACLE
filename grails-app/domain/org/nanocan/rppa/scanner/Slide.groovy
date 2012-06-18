package org.nanocan.rppa.scanner

import org.nanocan.rppa.layout.SlideLayout

class Slide {

    Date dateOfStaining
    Experimenter experimenter
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
        (dateOfStaining.toGMTString() + "_" + experimenter.toString() + "_" + antibody.toString())
    }
}
