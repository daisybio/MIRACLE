package org.nanocan.rppa.scanner

class Spot implements Comparable {

    double FG
    double BG
    int x
    int y
    int diameter
    int flag
    int block
    int row
    int column

    static constraints = {
    }

    String toString()
    {
        "B/C/R: ${block}/${column}/${row}, FG/BG: ${FG}/${BG}, flag: ${flag}"
    }

    //makes samples sortable in order row -> block -> column
    public int compareTo(def other) {
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
