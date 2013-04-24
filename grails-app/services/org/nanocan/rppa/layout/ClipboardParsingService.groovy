package org.nanocan.rppa.layout

import java.util.regex.Matcher
import java.util.regex.Pattern

class ClipboardParsingService {

	def parse(String pastedText, SlideLayout layout) {
		//def txt = pastedText
		def txt = pastedText.split("\n")
		def nrRows = pastedText.count("\n") + 1
		def nrColumns = txt[0].count("\t") + 1
		println "\n #rows: " + nrRows + "\n #columns: " + nrColumns
		ArrayList units = []
		pastedText.splitEachLine("\t",{units << it})
		/*
		def x = 0
		for (int j = 0 ; j < nrRows ; j++){
			ArrayList lines = []
			txt[j].splitEachLine("\t",{lines << it})
			for (int i = 0 ; i < nrColumns ; i++){
				units[x] = lines[0][i]
				x++
			}
		}
		*/
		def x = 0
		layout.sampleSpots.each {
			int row = (x/nrColumns)
			int column = (x%nrColumns)
			println "row: " + row + " column: " + column
			it.cellLine = CellLine.findByNameIlike(units[row][column])
			x++
		}
		
		return layout.sampleSpots
	}
}
