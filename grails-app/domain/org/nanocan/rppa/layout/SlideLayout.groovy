package org.nanocan.rppa.layout

import org.nanocan.rppa.security.Person

class SlideLayout{

    Date dateCreated
    Date lastUpdated

    Person createdBy
    Person lastUpdatedBy
    
    String title
    int columnsPerBlock
    int rowsPerBlock
    int numberOfBlocks
    String depositionPattern

    static hasMany = [sampleSpots: LayoutSpot]

    SortedSet sampleSpots

    static constraints = {
        title unique: true
        columnsPerBlock min:  1
        rowsPerBlock min:  1
        numberOfBlocks min:  1
    }
    
    String toString(){
        title
    }
}
