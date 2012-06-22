package org.nanocan.rppa.project

import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.rppa.scanner.Slide
import org.nanocan.rppa.security.Person

class Project {

    String projectTitle
    String projectDescription

    Date dateCreated
    Date lastUpdated

    Person createdBy
    Person lastUpdatedBy

    static hasMany = [layouts: SlideLayout, slides: Slide]

    static constraints = {

        projectTitle unique:true
    }

    String toString()
    {
        projectTitle
    }
}
