package org.nanocan.rppa.scanner

class ResultFile {

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
