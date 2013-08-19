package org.nanocan.rppa.security

import grails.plugins.springsecurity.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])

class IndexController {

    def index() { }
}
