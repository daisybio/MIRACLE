package org.nanocan.rppa.layout

class SlideLayout{
    
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
