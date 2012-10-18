package org.nanocan.savanah.extraction

import org.nanocan.savanah.plates.Plate
import groovy.transform.InheritConstructors

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 18-10-12
 * Time: 10:28
 */
@InheritConstructors
class ExtractionColumnWiseIterator extends ExtractionIteratorBase implements Iterator{

    @Override
    boolean hasNext() {
        if(currentExtractionRow > extractionRows || currentExtractionColumn > extractionColumns) return false
        else return true
    }

    @Override
    Object next() {
        def extraction = extract(currentExtractionColumn, currentExtractionRow)

        if(currentExtractionRow == extractionRows)
        {
            currentExtractionRow = 1
            currentExtractionColumn++

        }
        else currentExtractionRow++

        return(extraction)
    }

    @Override
    void remove() {
    }
}