package org.nanocan.rppa.scanner

import rppa.layout.SlideLayout

class Slide {

    Date dateOfStaining
    Experimenter experimenter
    int laserWavelength
    int PhotoMultiplierTube
    ResultFile resultFile
    ResultFile resultImage
    ResultFile protocol
    SlideLayout layout

    static hasMany = [spots: Spot, blockShifts: BlockShift]

    Antibody antibody

    static constraints = {
    }
}
