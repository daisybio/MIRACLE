package org.nanocan.savanah.security

class Person {

    String username

    static constraints = {
        username blank: false, unique: true
    }

    static mapping = {
        datasource 'SAVANAH'
    }

    String toString(){
        username
    }
}
