package org.nanocan.savanah.plates

import java.io.Serializable;

class PlateType implements Serializable{

    String name
    String vendor
    String description

    static constraints = {
    }

    String toString()
    {
        name
    }

    static mapping = {
        datasource "SAVANAH"
    }
}
