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

    static hasMany = [spots: Spot, blockShifts: BlockShift]

    Antibody antibody

    static constraints = {
         protocol nullable: true
    }
}
