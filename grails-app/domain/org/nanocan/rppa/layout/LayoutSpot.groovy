package org.nanocan.rppa.layout

import org.nanocan.rppa.rnai.Sample
import org.nanocan.rppa.scanner.Spot

class LayoutSpot implements Comparable{

    CellLine cellLine
    LysisBuffer lysisBuffer
    Dilution dilutionFactor
    Inducer inducer
    SpotType spotType
    Sample sample

    int block
    int col
    int row

    static belongsTo = SlideLayout
    static hasMany = [spots: Spot]
    SlideLayout layout

    static constraints = {
        cellLine nullable:  true
        lysisBuffer nullable: true
        dilutionFactor nullable: true
        inducer nullable: true
        sample nullable:  true
        spotType nullable:  true
    }

    static mapping = {
        layout index: 'lspot_idx'
        block index: 'lspot_idx'
        col index: 'lspot_idx'
        row index: 'lspot_idx'
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
                    if(col < other.col) return -1
                    else if(col > other.col) return 1
                    else return 0
                }
            }
        }
    }

    String toString()
    {
        "B/C/R: ${block}/${col}/${row}"
    }
}
