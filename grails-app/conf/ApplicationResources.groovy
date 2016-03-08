/*
 * Copyright (C) 2014
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:			http://www.nanocan.org/miracle/
 * ###########################################################################
 *	
 *	This file is part of MIRACLE.
 *
 *  MIRACLE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
modules = {
    application {
        resource url:'js/application.js'
    }
    overrides {
        'jquery-theme' {
            resource id:'theme',
                    url:[dir: 'css',
                         file:'jquery-ui-1.10.4.custom.min.css'],
                    attrs:[media:'screen, projection']
        }
    }

    colorPicker {
        dependsOn 'jquery'
        resource url: 'js/colorpicker.js'
        resource url: 'css/colorpicker.css'
    }

    highcharts {
        resource url: 'js/highcharts/highcharts.js'
        resource url: 'js/highcharts/data.js'
        resource url: 'js/highcharts/exporting.js'
    }

    highchartsHeatmap{
        dependsOn 'highcharts'
        resource url: 'js/highcharts/heatmap.js'
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

    syntaxhighlighter{
        resource url: '/js/shCore.js'
        resource url: '/js/shBrushXml.js'
        resource url: '/js/shBrushR.js'
        resource url: '/css/shCore.css'
        resource url: '/css/shCoreDefault.css'
        resource url: '/css/shThemeDefault.css'
    }
}