package rppascanner

class Slide {

    Date dateOfStaining
    Experimenter experimenter
    int laserWavelength
    ResultFile resultFile
    ResultFile resultImage

    static hasMany = [spots: Spot, blockShifts: BlockShift]

    Antibody antibody

    static constraints = {
    }
}
