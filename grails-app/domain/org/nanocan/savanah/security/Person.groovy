package org.nanocan.savanah.security

import java.io.Serializable;

class Person implements Serializable{

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
