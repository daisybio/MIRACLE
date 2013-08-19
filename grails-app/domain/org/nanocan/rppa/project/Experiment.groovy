package org.nanocan.rppa.project

import org.nanocan.rppa.security.Person
import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.rppa.scanner.Slide
import org.nanocan.rppa.layout.PlateLayout

class Experiment implements Serializable{

    String title
    String description

    Date dateCreated
    Date lastUpdated
    Date firstDayOfTheExperiment

    Person createdBy
    Person lastUpdatedBy

    static hasMany = [layouts: SlideLayout, slides: Slide, plateLayouts: PlateLayout]
    static belongsTo = [project: Project]

    static constraints = {

        title unique:true
        firstDayOfTheExperiment nullable: true
    }

    String toString()
    {
        /*if(firstDayOfTheExperiment)
            return ("${firstDayOfTheExperiment.toString()} - ${title}")
        else */return title
    }
}
