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

    private class SortRowWise implements Comparator<WellLayout>{

        @Override
        int compare(WellLayout one, WellLayout other) {
            if(one.col < other.col) return -1
            else if(one.col > other.col) return 1
            else{
                if(one.row < other.row) return -1
                else if(one.row > other.row) return 1
                else return 0
            }
        }
    }


    @Override
    void nextSpot() {
        if(currentSpottingColumn < maxSpottingColumns) currentSpottingColumn++;

        else{
            currentSpottingRow++
            currentSpottingColumn = 1
        }
    }

    @Override
    List<WellLayout> sortExtraction(List<WellLayout> extraction) {
        return extraction.sort(sorter)
    }
}