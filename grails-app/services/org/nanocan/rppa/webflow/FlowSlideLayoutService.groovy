package org.nanocan.rppa.webflow

import org.nanocan.rppa.layout.LayoutSpot
import org.nanocan.rppa.layout.SlideLayout

class FlowSlideLayoutService {
	def grailsApplication
	def idCountingService

	/**
	 * Creates a full set of layout spots with respect to the number of columns, blocks and rows.
	 * @param slideLayout
	 * @return
	 */
	def createSampleSpots(SlideLayout slideLayout) {

		for (int block = 1; block <= slideLayout.numberOfBlocks; block++) {
			for (int col = 1; col <= slideLayout.columnsPerBlock; col++) {
				for (int row = 1; row <= slideLayout.rowsPerBlock; row++) {
					def newId = idCountingService.getId("LayoutSpot")
					def spot = new LayoutSpot(block: block, col: col, row: row, layout: slideLayout)
					spot.id = newId
					slideLayout.addToSampleSpots(spot)
				}
			}
		}
	}
}
