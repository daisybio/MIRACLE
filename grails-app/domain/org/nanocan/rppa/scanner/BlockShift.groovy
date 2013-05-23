package org.nanocan.rppa.scanner

import java.io.Serializable;

class BlockShift implements Serializable{

    int horizontalShift
    int verticalShift
    int blockNumber

    static constraints = {
    }

    static belongsTo = [slide: Slide]
}
