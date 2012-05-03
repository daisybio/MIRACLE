modules = {
    application {
        resource url:'js/application.js'
    }

    colorPicker {
        dependsOn 'jquery'
        resource url: 'js/colorpicker.js'
        resource url: 'css/colorpicker.css'
    }
}