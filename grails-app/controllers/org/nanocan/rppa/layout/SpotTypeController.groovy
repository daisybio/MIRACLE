package org.nanocan.rppa.layout

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class SpotTypeController {

    def scaffold = true

    static navigation = [
            group : "layout",
            title: "Spot Type"
    ]
}
