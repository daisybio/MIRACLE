package org.nanocan.rppa.project

import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.rppa.scanner.Slide
import org.nanocan.rppa.security.Person
import org.nanocan.rppa.layout.PlateLayout

class Project {

    String projectTitle
    String projectDescription

    Date dateCreated
    Date lastUpdated

    Person createdBy
    Person lastUpdatedBy

    static hasMany = [layouts: SlideLayout, slides: Slide, plateLayouts: PlateLayout]

    static constraints = {

        projectTitle unique:true
    }

    String toString()
    {
        projectTitle
    }
}
