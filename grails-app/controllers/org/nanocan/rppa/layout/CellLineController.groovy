package org.nanocan.rppa.layout

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class CellLineController {

    def scaffold = true

    static navigation = [
          group: 'layout',
          title: 'CellLine'
    ]

}
