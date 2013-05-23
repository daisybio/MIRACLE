package org.nanocan.rppa.scanner

import java.io.Serializable;

class ResultFile implements Serializable{

    String fileName
    String filePath
    Date dateUploaded
    String fileType

    String toString()
    {
        fileName
    }

    static constraints = {

        fileName()
        filePath()
        dateUploaded()
        fileType inList: ["Result", "Image", "Protocol"]
    }
}
