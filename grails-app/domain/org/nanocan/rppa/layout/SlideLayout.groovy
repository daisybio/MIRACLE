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
        title unique: true, blank: false
        columnsPerBlock min:  1
        rowsPerBlock min:  1
        numberOfBlocks min:  1
        depositionPattern validator: {
            if(!(it ==~ /\[([1-9],)+[1-9]\]/)) return ("default.deposition.pattern.mismatch")
        }
    }
    
    String toString(){
        title
    }
}
