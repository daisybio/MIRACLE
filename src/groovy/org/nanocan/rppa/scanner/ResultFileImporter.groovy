package org.nanocan.rppa.scanner

/**
 * This class extends the AbstractExcelImporter of the excel-import plugin.
 * Its purpose is to parse an excel sheet to a list of spots and to
 * make the sheet names available.
 */

import org.grails.plugins.excelimport.*
import static org.grails.plugins.excelimport.ExpectedPropertyType.*
import org.nanocan.rppa.scanner.ResultFileConfig

class ResultFileImporter extends AbstractExcelImporter{

    def static cellReporter = new DefaultImportCellCollector()

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

    Map getSheets()
    {
        def sheetNames = [:]

        for(int i = 0;  i < workbook.getNumberOfSheets(); i++)
        {
            sheetNames[i] = workbook.getSheetName(i)
        }

        return(sheetNames)
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
