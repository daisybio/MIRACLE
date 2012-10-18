package org.nanocan.rppa.spotting

import org.nanocan.savanah.plates.WellLayout
import org.nanocan.rppa.layout.LayoutSpot
import org.nanocan.rppa.layout.CellLine
import org.nanocan.rppa.layout.SlideLayout

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 18-10-12
 * Time: 16:58
 */
class LeftToRightSpotter extends Spotter{

    def slideLayout = new SlideLayout()

    def sorter = new SortRowWise()
    def currentSpottingColumn = 1
    def maxSpottingColumns = ?

    def currentSpottingRow = 1
    def maxSpottingRows = ?

    @Override
    def spot(List<WellLayout> extraction, extractorColumns) {

        def extractionSorted = extraction.sort(sorter)

        extraction.each{
            def cellLine

            if(it.cellLine) cellLine = CellLine.findByName(it.cellLine.name)

            if(!cellLine)
            {
                cellLine = new CellLine(name: it.cellLine.name, color: it.cellLine.color)
                cellLine.save(flush: true)
            }

            //repeat cellLine procedure with inducer, treatment and lysisbuffer
            //where to get dilutionFactor from???

            def newLayoutSpot = new LayoutSpot(block: calculateBlockFromRowAndCol(it.row, it.col, extractorColumns),
            cellLine: cellLine, dilutionFactor: ?, col: currentSpottingColumn, row: currentSpottingRow, inducer: inducer,
            lysisBuffer: lysisBuffer, treatment: treatment, layout: slideLayout)

            slideLayout.addToSampleSpots(newLayoutSpot)
        }
    }

    def getSlideLayout()
    {
        return slideLayout
    }

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
}
