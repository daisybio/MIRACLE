package org.nanocan.rppa.spotting

import groovy.transform.InheritConstructors

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 18-10-12
 * Time: 16:58
 */
@InheritConstructors
class TopToBottomSpotter extends Spotter{

    def sortClosure = {one, other ->
        if(one.col < other.col) return -1
        else if(one.col > other.col) return 1
        else{
            if(one.row < other.row) return -1
            else if(one.row > other.row) return 1
            else return 0
        }
    }

    @Override
    void nextSpot() {
        if(currentSpottingRow < maxSpottingRows) currentSpottingRow++;

        else{
            currentSpottingColumn++
            currentSpottingRow = 1
        }
    }

    @Override
    def sortExtraction(extraction) {
        return extraction.sort(sortClosure)
    }
}
