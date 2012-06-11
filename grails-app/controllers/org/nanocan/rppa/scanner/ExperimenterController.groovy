package org.nanocan.rppa.scanner

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class ExperimenterController {

    def scaffold = true

    static navigation = [
            group: 'scanner',
            title: 'Experimenter'
    ]
}
