package org.nanocan.rppa.scanner

/**
 * This class extends the AbstractExcelImporter of the excel-import plugin.
 * Its purpose is to parse an excel sheet to a list of spots and to
 * make the sheet names available.
 */

import org.grails.plugins.excelimport.*
import static org.grails.plugins.excelimport.ExpectedPropertyType.*
import org.nanocan.rppa.scanner.ResultFileConfig
import org.apache.commons.io.FilenameUtils
import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.xssf.eventusermodel.XSSFReader
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.openxml4j.opc.PackageAccess
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class ResultFileImporter extends AbstractExcelImporter{

    def static cellReporter = new DefaultImportCellCollector()

    static getSheets(filePath){

        def fileEnding = FilenameUtils.getExtension(filePath)
        def sheets = []


        if(fileEnding == "xlsx")
        {
            OPCPackage pkg = OPCPackage.open(filePath, PackageAccess.READ);

            XSSFReader r = new XSSFReader(pkg)
            XSSFReader.SheetIterator iterator = (XSSFReader.SheetIterator) r.getSheetsData()

            while(iterator.hasNext())
            {
                iterator.next()
                sheets << iterator.getSheetName()
            }
        }

        else {
            def workbook = WorkbookFactory.create(new File(filePath));
            def numOfSheets = workbook.getNumberOfSheets()
            for (int i = 0; i < numOfSheets; i++)
            {
                sheets << workbook.getSheetName(i)
            }
        }

        return sheets
    }

    protected def customRead(filePath)
    {
        def fileEnding = FilenameUtils.getExtension(filePath)

        if(fileEnding == "xlsx") readXLSX(filePath)
        else readXLS(filePath)
    }

    protected def readXLSX(filePath)
    {
        OPCPackage pkg = OPCPackage.open(filePath, PackageAccess.READ);
        workbook = new SXSSFWorkbook(new XSSFWorkbook())
        workbook = WorkbookFactory.create(pkg)
        evaluator = workbook.creationHelper.createFormulaEvaluator()
    }

    protected def readXLS(filePath) {

        workbook = WorkbookFactory.create(new File(filePath))
        evaluator = workbook.creationHelper.createFormulaEvaluator()
    }

    static Map configuratiomMap = [
            BG: ([expectedType: DoubleType, defaultValue: 0.0]),
            FG: ([expectedType: DoubleType, defaultValue: 0.0]),
            block: ([expectedType: IntType, defaultValue: 0]),
            row: ([expectedType: IntType, defaultValue: 0]),
            column: ([expectedType: IntType, defaultValue: 0]),
            diameter: ([expectedType: DoubleType, defaultValue: 0.0]),
            flag: ([expectedType: IntType, defaultValue: 0]),
            x: ([expectedType: IntType, defaultValue: 0]),
            y: ([expectedType: IntType, defaultValue: 0])
    ]

    public ResultFileImporter()
    {
        super()
    }

    List<Map> getSpots(String sheetName, ResultFileConfig rfc) {

        Map CONFIG_SPOT_COLUMN_MAP = getExcelConfig(sheetName, rfc)

        ExcelImportService.getService().columns(
                workbook,
                CONFIG_SPOT_COLUMN_MAP,
                cellReporter,
                configuratiomMap
        )
    }

    Map getExcelConfig(String sheetName, ResultFileConfig rfc)
    {
        Map CONFIG_SPOT_COLUMN_MAP = [
                sheet: sheetName,
                startRow: rfc.skipLines+1,
                columnMap:  [
                        (rfc.bgCol) : 'BG',
                        (rfc.blockCol): 'block',
                        (rfc.columnCol):'col',
                        (rfc.diameterCol): 'diameter',
                        (rfc.fgCol): 'FG',
                        (rfc.flagCol): 'flag',
                        (rfc.rowCol): 'row',
                        (rfc.xCol): 'x',
                        (rfc.yCol): 'y'
                ]
        ]

        return CONFIG_SPOT_COLUMN_MAP
    }
}
