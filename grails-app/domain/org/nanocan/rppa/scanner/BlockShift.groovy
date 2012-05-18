package org.nanocan.rppa.scanner

class BlockShift {

    int horizontalShift
    int verticalShift
    int blockNumber

    static constraints = {
    }

    static belongsTo = [slide: Slide]
}
