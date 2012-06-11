package org.nanocan.rppa.rnai

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class IdentifierController {

    def scaffold = true
}
