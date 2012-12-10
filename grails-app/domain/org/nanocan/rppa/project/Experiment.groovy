package org.nanocan.rppa.project

import org.nanocan.rppa.security.Person
import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.rppa.scanner.Slide
import org.nanocan.rppa.layout.PlateLayout

class Experiment {

    String title
    String description

    Date dateCreated
    Date lastUpdated

    Person createdBy
    Person lastUpdatedBy

    static hasMany = [layouts: SlideLayout, slides: Slide, plateLayouts: PlateLayout]
    static belongsTo = [project: Project]

    static constraints = {

        title unique:true
    }

    String toString()
    {
        title
    }
}
