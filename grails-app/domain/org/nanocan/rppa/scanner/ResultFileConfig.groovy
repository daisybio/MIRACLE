package org.nanocan.rppa.scanner

import java.io.Serializable;

class ResultFileConfig implements Serializable{

    String name

    String blockCol
    String rowCol
    String columnCol
    String fgCol
    String bgCol
    String flagCol
    String xCol
    String yCol
    String diameterCol

    int skipLines

    static constraints = {
        unique: "name"
    }

    String toString()
    {
        name
    }
}
