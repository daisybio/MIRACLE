package org.nanocan.rppa.scanner

import grails.plugin.spock.UnitSpec
import spock.lang.Shared

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 20-06-12
 * Time: 11:13
 */
class SpotImportServiceSpec extends UnitSpec {

      @Shared spotImportService = new SpotImportService()

      def "load an XLS file and get sheet names"()
      {
          setup:
          Mock(ResultFile)
          Mock(Slide)

          when:
          def sheets = spotImportService.getSheets(slideInstance)

          then:
          sheets[0] == "Slides" && sheets[3] == "GAPDH 0,01 PMT1"

          where:
          resultFile = new ResultFile(filePath: "sampleData/2012-01-19 MLP - orginal.xls", fileName: "2012-01-19 MLP - orginal.xls")
          slideInstance = new Slide(resultFile: resultFile)
      }

      def "test mapping of column 6 on layout column 1"()
      {
          when:
          def layoutColumn = spotImportService.mapToLayoutColumn(6, 6)

          then:
          layoutColumn == 1
      }

    def "test mapping of column 1 on layout column 1"()
    {
        when:
        def layoutColumn = spotImportService.mapToLayoutColumn(1, 6)

        then:
        layoutColumn == 1
    }

    def "test mapping of column 7 on layout column 2"()
    {
        when:
        def layoutColumn = spotImportService.mapToLayoutColumn(7, 6)

        then:
        layoutColumn == 2
    }

    def "test mapping of column 13 on layout column 3"()
    {
        when:
        def layoutColumn = spotImportService.mapToLayoutColumn(13, 6)

        then:
        layoutColumn == 3
    }

    def "test mapping of column 12 on layout column 2"()
    {
        when:
        def layoutColumn = spotImportService.mapToLayoutColumn(12, 6)

        then:
        layoutColumn == 2
    }

}
