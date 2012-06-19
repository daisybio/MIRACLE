package org.nanocan.rppa.scanner

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class ResultFileConfigController {

    def scaffold = true

    static navigation = [
            group: 'scanner',
            title: 'Result File Config'
    ]
}
