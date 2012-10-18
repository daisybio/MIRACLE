package savanah

import org.nanocan.savanah.plates.Plate
import org.nanocan.savanah.plates.PlateLayout
import org.nanocan.savanah.plates.WellLayout
import org.nanocan.savanah.extraction.ExtractionRowWiseIterator
import grails.plugin.spock.UnitSpec
import grails.test.mixin.Mock
import spock.lang.Shared

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


}
