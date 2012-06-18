package org.nanocan.rppa.scanner

import org.nanocan.rppa.layout.LayoutSpot
import org.nanocan.rppa.layout.SlideLayout
import org.hibernate.FetchMode
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.text.DecimalFormatSymbols
import java.text.DecimalFormat
import org.hibernate.criterion.Restrictions
import org.hibernate.StatelessSession
import org.hibernate.Transaction

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
