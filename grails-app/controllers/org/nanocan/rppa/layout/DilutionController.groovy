package org.nanocan.rppa.layout

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class DilutionController {

    def scaffold = true
}
