package rppascanner



import org.junit.*
import grails.test.mixin.*

@TestFor(SlideController)
@Mock(Slide)
class SlideControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/slide/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.slideInstanceList.size() == 0
        assert model.slideInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.slideInstance != null
    }

    void testSave() {
        controller.save()

        assert model.slideInstance != null
        assert view == '/slide/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/slide/show/1'
        assert controller.flash.message != null
        assert Slide.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/slide/list'


        populateValidParams(params)
        def slide = new Slide(params)

        assert slide.save() != null

        params.id = slide.id

        def model = controller.show()

        assert model.slideInstance == slide
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/slide/list'


        populateValidParams(params)
        def slide = new Slide(params)

        assert slide.save() != null

        params.id = slide.id

        def model = controller.edit()

        assert model.slideInstance == slide
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/slide/list'

        response.reset()


        populateValidParams(params)
        def slide = new Slide(params)

        assert slide.save() != null

        // test invalid parameters in update
        params.id = slide.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/slide/edit"
        assert model.slideInstance != null

        slide.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/slide/show/$slide.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        slide.clearErrors()

        populateValidParams(params)
        params.id = slide.id
        params.version = -1
        controller.update()

        assert view == "/slide/edit"
        assert model.slideInstance != null
        assert model.slideInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/slide/list'

        response.reset()

        populateValidParams(params)
        def slide = new Slide(params)

        assert slide.save() != null
        assert Slide.count() == 1

        params.id = slide.id

        controller.delete()

        assert Slide.count() == 0
        assert Slide.get(slide.id) == null
        assert response.redirectedUrl == '/slide/list'
    }
}
