package org.nanocan.savanah.extraction

import org.nanocan.savanah.plates.Plate
import groovy.transform.InheritConstructors

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 18-10-12
 * Time: 09:41
 */
@InheritConstructors
class ExtractionRowWiseIterator extends ExtractionIteratorBase implements Iterator{

    @Override
    boolean hasNext() {
        if(currentExtractionRow > extractionRows || currentExtractionColumn > extractionColumns) return false
        else return true
    }

    @Override
    Object next() {
        def extraction = extract(currentExtractionColumn, currentExtractionRow)

        if(currentExtractionColumn == extractionColumns)
        {
            currentExtractionColumn = 1
            currentExtractionRow++
        }
        else currentExtractionColumn++

        return(extraction)
    }

    @Override
    void remove() {
    }
}