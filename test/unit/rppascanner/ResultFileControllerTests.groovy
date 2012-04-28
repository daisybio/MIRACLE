package rppascanner



import org.junit.*
import grails.test.mixin.*

@TestFor(ResultFileController)
@Mock(ResultFile)
class ResultFileControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/resultFile/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.resultFileInstanceList.size() == 0
        assert model.resultFileInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.resultFileInstance != null
    }

    void testSave() {
        controller.save()

        assert model.resultFileInstance != null
        assert view == '/resultFile/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/resultFile/show/1'
        assert controller.flash.message != null
        assert ResultFile.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/resultFile/list'


        populateValidParams(params)
        def resultFile = new ResultFile(params)

        assert resultFile.save() != null

        params.id = resultFile.id

        def model = controller.show()

        assert model.resultFileInstance == resultFile
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/resultFile/list'


        populateValidParams(params)
        def resultFile = new ResultFile(params)

        assert resultFile.save() != null

        params.id = resultFile.id

        def model = controller.edit()

        assert model.resultFileInstance == resultFile
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/resultFile/list'

        response.reset()


        populateValidParams(params)
        def resultFile = new ResultFile(params)

        assert resultFile.save() != null

        // test invalid parameters in update
        params.id = resultFile.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/resultFile/edit"
        assert model.resultFileInstance != null

        resultFile.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/resultFile/show/$resultFile.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        resultFile.clearErrors()

        populateValidParams(params)
        params.id = resultFile.id
        params.version = -1
        controller.update()

        assert view == "/resultFile/edit"
        assert model.resultFileInstance != null
        assert model.resultFileInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/resultFile/list'

        response.reset()

        populateValidParams(params)
        def resultFile = new ResultFile(params)

        assert resultFile.save() != null
        assert ResultFile.count() == 1

        params.id = resultFile.id

        controller.delete()

        assert ResultFile.count() == 0
        assert ResultFile.get(resultFile.id) == null
        assert response.redirectedUrl == '/resultFile/list'
    }
}
