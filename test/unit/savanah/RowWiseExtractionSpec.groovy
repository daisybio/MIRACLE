package savanah

import grails.plugin.spock.UnitSpec
import grails.test.mixin.Mock
import spock.lang.Shared
import org.nanocan.rppa.layout.PlateLayout
import org.nanocan.savanah.extraction.ExtractionRowWiseIterator
import org.nanocan.rppa.layout.WellLayout

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 18-10-12
 * Time: 10:32
 */
@Mock([Plate, PlateLayout, WellLayout])
class RowWiseExtractionSpec extends UnitSpec{

    @Shared plate
    @Shared iterator

    def setup(){
        def plateLayout = new PlateLayout(name: "test layout", format: "384-well", cols: 24, rows: 16)
        plateLayout.save()

        for (int col = 1; col <= plateLayout.cols; col++) {
            for (int row = 1; row <= plateLayout.rows; row++) {
                plateLayout.addToWells(new WellLayout(col: col, row: row, layout: plateLayout))
            }
        }
        plateLayout.save()

        plate = new Plate(cols: 24, rows:  16, barcode: "test", family: "daughter", format: "384-well", name: "test plate", layout: plateLayout)
        plate.save()

        iterator = new ExtractionRowWiseIterator(plate: plate)

    }

    def "test row wise iteration first element"()
    {
        setup:
        def extraction = []

        when:
        extraction = iterator.next()

        then:
        extraction.first().row == 1
        extraction.first().col == 1
        extraction.last().row == 4
        extraction.last().col == 12
    }

    def "test row wise iteration second element"()
    {
        setup:
        def extraction = []

        when:
        iterator.next()
        extraction = iterator.next()

        then:
        extraction.first().row == 1
        extraction.first().col == 13
        extraction.last().row == 4
        extraction.last().col == 24
    }

    def "test row wise iteration third element"()
    {
        setup:
        def extraction = []

        when:
        iterator.next()
        iterator.next()
        extraction = iterator.next()

        then:
        extraction.first().row == 5
        extraction.first().col == 1
        extraction.last().row == 8
        extraction.last().col == 12
    }

    def "test that there are 8 extractions"(){
        setup:
        def counter = 0

        when:
        while(iterator.hasNext()){
            iterator.next()
            counter++
        }

        then:
        counter == 8
    }

    def "test that next returns null when hasNext is false"(){
        when:
        while(iterator.hasNext()) iterator.next()

        then: iterator.next() == null

    }

    def "test extraction of a 96 well plate"(){
        setup:
        def plateLayout96 = new PlateLayout(name: "test layout 96", format: "96-well", cols: 12, rows: 8)
        plateLayout96.save()

        for (int col = 1; col <= plateLayout96.cols; col++) {
            for (int row = 1; row <= plateLayout96.rows; row++) {
                plateLayout96.addToWells(new WellLayout(col: col, row: row, layout: plateLayout96))
            }
        }
        plateLayout96.save()

        def plate96 = new Plate(cols: 12, rows:  8, barcode: "test 96", family: "daughter", format: "96-well", name: "test plate 96", layout: plateLayout96)
        plate96.save()

        def iterator96 = new ExtractionRowWiseIterator(plate: plate96, extractorCols: 6, extractorRows: 2)
        def counter = 0

        when:
        def extractions = []

        while(iterator96.hasNext()){
            extractions << iterator96.next()
            counter++
        }

        then:
        counter == 8
        extractions.first().size() == 12
        extractions.first().first() instanceof WellLayout == true
        extractions.last().size() == 12
        extractions.size() == 8
    }


}
