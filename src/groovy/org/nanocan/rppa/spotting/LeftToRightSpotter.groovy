package org.nanocan.rppa.spotting

import org.nanocan.savanah.plates.WellLayout as SavanahWellLayout
import org.nanocan.rppa.layout.WellLayout as MiracleWellLayout
import groovy.transform.InheritConstructors

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 18-10-12
 * Time: 16:58
 */
@InheritConstructors
class LeftToRightSpotter extends Spotter{

    def currentSpottingRowUsed = true

    def sortClosure = { one, other ->
        if(one.row < other.row) return -1
        else if(one.row > other.row) return 1
        else{
            if(one.col < other.col) return -1
            else if(one.col > other.col) return 1
            else return 0
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
    def sortExtraction(def extraction) {
        return extraction.sort(sortClosure)
    }
}
