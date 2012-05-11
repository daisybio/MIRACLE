package org.nanocan.rppa.layout

class LayoutSpot implements Comparable{

    CellLine cellLine
    LysisBuffer lysisBuffer
    Dilution dilutionFactor

    int block
    int column
    int row

    static belongsTo = SlideLayout
    SlideLayout layout

    static constraints = {
        cellLine nullable:  true
        lysisBuffer nullable: true
        dilutionFactor nullable: true
    }

    //makes samples sortable in order block -> column -> row
    public int compareTo(def other) {
        //first compare in which 12er this spot is
        def blockTab = (int) ((block-1) / 12)
        def otherBlockTab = (int) ((other.block-1) /12)

        if(blockTab < otherBlockTab) return -1
        else if(blockTab > otherBlockTab) return 1
        else
        {
            if(row < other.row) return -1
            else if(row > other.row) return 1
            else{
                if(block < other.block) return -1
                else if(block > other.block) return 1
                else{
                    if(column < other.column) return -1
                    else if(column > other.column) return 1
                    else return 0
                }
            }
        }
    }

    String toString()
    {
        "B/C/R: ${block}/${column}/${row}"
    }
}
