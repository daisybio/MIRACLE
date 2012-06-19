package org.nanocan.rppa.scanner

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class SpotController {

    def scaffold = true
}
