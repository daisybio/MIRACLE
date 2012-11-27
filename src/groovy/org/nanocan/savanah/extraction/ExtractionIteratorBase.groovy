package org.nanocan.savanah.extraction

import org.nanocan.savanah.plates.WellLayout

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 18-10-12
 * Time: 10:25
 */
class ExtractionIteratorBase {

    def extractionRows
    def extractionColumns
    def currentExtractionRow
    def currentExtractionColumn
    def extractorRows
    def extractorCols
    def plateLayout

    ExtractionIteratorBase(Map map){

        this.extractorCols = map.extractorCols?:12
        this.extractorRows = map.extractorRows?:4
        this.plateLayout = map.plateLayout

        //calculate number of extraction rows and columns
        extractionColumns = plateLayout.cols / extractorCols
        extractionRows = plateLayout.rows / extractorRows

        //set initial values
        currentExtractionColumn = 1
        currentExtractionRow = 1
    }

    def extract(int currentExtractionColumn, int currentExtractionRow){
        if(!plateLayout) return null
        if(currentExtractionColumn > extractionColumns || currentExtractionRow > extractionRows) return null

        def startColumn = ((currentExtractionColumn - 1) * extractorCols) + 1
        def startRow = ((currentExtractionRow - 1) * extractorRows) + 1

        def endColumn = startColumn + extractorCols -1
        def endRow = startRow + extractorRows - 1

        def extraction

        if(plateLayout instanceof org.nanocan.savanah.plates.PlateLayout)
        {
            extraction = WellLayout.withCriteria{
                plateLayout{
                    eq("id", plateLayout.id)
                }
                between("row", startRow, endRow)
                between("col", startColumn, endColumn)
            }
        }
        else {
            extraction = org.nanocan.rppa.layout.WellLayout.withCriteria{
                plateLayout{
                    eq("id", plateLayout.id)
                }
                between("row", startRow, endRow)
                between("col", startColumn, endColumn)
            }
        }

        return(extraction)
    }
}
