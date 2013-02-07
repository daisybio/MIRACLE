package org.nanocan.rppa.io

import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.openxml4j.opc.PackageAccess
import org.apache.poi.xssf.model.StylesTable
import org.xml.sax.ContentHandler
import org.xml.sax.InputSource
import org.xml.sax.XMLReader

import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable
import org.apache.poi.xssf.eventusermodel.XSSFReader
import org.nanocan.excelimport.MyXLSXHandler

/**
 * Created with IntelliJ IDEA.
 * User: markus
 * Date: 2/7/13
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
class XlsxImportService {


    def parseXLSXSheetToCSV(filePath, sheetIndex) {

        OPCPackage xlsxPackage = OPCPackage.open(filePath, PackageAccess.READ)

        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(xlsxPackage)
        XSSFReader reader = new XSSFReader(xlsxPackage)
        StylesTable styles = reader.getStylesTable()
        def instream = reader.getSheet("rId" + sheetIndex)

        InputSource sheetSource = new InputSource(instream)
        SAXParserFactory saxFactory = SAXParserFactory.newInstance()
        SAXParser saxParser = saxFactory.newSAXParser()
        XMLReader sheetParser = saxParser.getXMLReader()

        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream ps = new PrintStream(baos)

        ContentHandler handler = new MyXLSXHandler(styles, strings, 5, ps)
        sheetParser.setContentHandler(handler)
        sheetParser.parse(sheetSource)

        instream.close()
        ps.close()

        def result = baos.toString()
        baos.close()

        return result
    }

}









