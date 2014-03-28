modules = {
    application {
        resource url:'js/application.js'
    }

    colorPicker {
        dependsOn 'jquery'
        resource url: 'js/colorpicker.js'
        resource url: 'css/colorpicker.css'
    }

    jstree {
        dependsOn 'jquery'
        resource url: 'js/jquery.jstree.js'
    }

    rainbowVis {
        resource url: 'js/rainbowvis.js'  , disposition: 'head'
    }

    bootstrap {
        resource url: 'js/bootstrap-dropdown.js', disposition: 'head'
        resource url: 'js/bootstrap.js'
        resource url: 'css/bootstrap.css'
    }

    polychart2 {
        resource url: 'js/polychart2.standalone.js'
    }

    select2{
        dependsOn 'jquery'
        resource url: 'js/select2-3.4.5/select2.min.js'
        resource url: 'js/select2-3.4.5/select2.css'
    }
}