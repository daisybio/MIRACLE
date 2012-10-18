package org.nanocan.rppa.spotting

import org.nanocan.rppa.layout.SlideLayout
import org.nanocan.savanah.plates.PlateLayout
import org.nanocan.savanah.plates.WellLayout

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 18-10-12
 * Time: 16:45
 */
abstract class Spotter {

    def slideLayout = new SlideLayout()

    abstract def spot(def extraction)

    private class SortColumnWise implements Comparator<WellLayout>{

        @Override
        int compare(WellLayout one, WellLayout other) {
            if(one.row < other.row) return -1
            else if(one.row > other.row) return 1
            else{
                if(one.col < other.col) return -1
                else if(one.col > other.col) return 1
                else return 0
            }
        }
    }

    def calculateBlockFromRowAndCol(int row, int col, int totalColumns)
    {
        (((row - 1) * totalColumns) + col)
    }
}
