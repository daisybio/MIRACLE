package rppascanner

class Slide {

    Date dateOfStaining
    Experimenter experimenter
    int laserWavelength
    ResultFile resultFile
    ResultFile resultImage

    static hasMany = [spots: Spot]

    PrimaryAntibody primaryAntibody
    SecondaryAntibody secondaryAntibody

    static constraints = {
    }
}
