package org.nanocan.rppa.scanner

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class AntibodyController {

    static navigation = [
            group: 'scanner',
            title: 'Antibody'
    ]

    def scaffold = true
}
