package org.nanocan.rppa.scanner

import org.nanocan.rppa.layout.LayoutSpot

class Spot implements Comparable {

    double FG
    double BG
    int x
    int y
    int diameter
    int flag
    int block
    int row
    int col

    static constraints = {
    }

    static belongsTo = [slide: Slide, layoutSpot: LayoutSpot]

    String toString()
    {
        "B/C/R: ${block}/${col}/${row}, FG/BG: ${FG}/${BG}, flag: ${flag}"
    }

    //makes samples sortable in order row -> block -> column
    public int compareTo(def other) {
        if(row < other.row) return -1
        else if(row > other.row) return 1
        else{
            if(block < other.block) return -1
            else if(block > other.block) return 1
            else{
                if(column < other.col) return -1
                else if(column > other.col) return 1
                else return 0
            }
        }
    }
}
