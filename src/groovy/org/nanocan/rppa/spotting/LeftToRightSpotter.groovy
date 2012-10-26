package org.nanocan.rppa.spotting

import org.nanocan.savanah.plates.WellLayout
import groovy.transform.InheritConstructors

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 18-10-12
 * Time: 16:58
 */
@InheritConstructors
class LeftToRightSpotter extends Spotter{

    def sorter = new SortRowWise()
    def currentSpottingRowUsed = true

    private class SortRowWise implements Comparator<WellLayout>{

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


    @Override
    void nextSpot() {
        if(currentSpottingColumn < maxSpottingColumns){
            currentSpottingColumn++
            currentSpottingRowUsed = true
        }

        else{
            currentSpottingRow++
            currentSpottingColumn = 1
            currentSpottingRowUsed = false
        }
    }

    @Override
    List<WellLayout> sortExtraction(List<WellLayout> extraction) {
        return extraction.sort(sorter)
    }
}
