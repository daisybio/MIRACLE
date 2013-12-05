package org.nanocan.rppa.scanner

class SlideService {

    def persistBlockShiftPattern(Slide slideInstance, Map hshift, Map vshift) {
        for (int block in 1..(slideInstance.layout?.numberOfBlocks ?: 48)) {
            def existingBlock = BlockShift.findBySlideAndBlockNumber(slideInstance, block)
            if (existingBlock) {
                existingBlock.horizontalShift = Integer.parseInt(hshift["hshift_${block}"])
                existingBlock.verticalShift = Integer.parseInt(vshift["vshift_${block}"])
                existingBlock.save(flush: true)
            }
            else {
                slideInstance.addToBlockShifts(new BlockShift(blockNumber: block, horizontalShift: hshift["hshift_${block}"],
                        verticalShift: vshift["vshift_${block}"]))
            }
        }

        return slideInstance
    }
}
