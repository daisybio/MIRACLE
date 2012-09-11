package org.nanocan.rppa.scanner

import java.awt.image.BufferedImage

class RppaService {

    def rconnectService
    def roperationsService

    def openConnectionAndTransferSlide(int slideId) {

        def slideInstance = Slide.get(slideId)
        def rConnection = rconnectService.getConnection()

        def tempDir = roperationsService.createTempDir(rConnection)

        roperationsService.setWorkingDirectory(rConnection, tempDir)

        rConnection.eval("rm(list=ls())")
        boolean rppaLoaded = rConnection.eval("require(rppa)")

        if(!rppaLoaded){
            rConnection.close()
            //throw new Exception("Could not load necessary R libraries!")
        }

        rConnection.eval("spots <- rppa.load(baseUrl=\"http://192.168.56.101:8080/MIRACLE/spotExport/\", slideIndex=${slideId})")
        rConnection.eval("spots <- rppa.filter.diameter(spots)")
        //rConnection.eval("spots <- rppa.filter.flagged(spots)")
        rConnection.eval("spots <- rppa.filter.neg.values(spots)")
        //rConnection.eval("spots <- spots[,c(\"Block\", \"Row\", \"Column\", \"Signal\", \"Deposition\", \"DilutionFactor\")]")
        rConnection.assign("title", slideInstance.toString())

        int blocksPerRow
        if (slideInstance.layout.blocksPerRow) blocksPerRow = slideInstance.layout.blocksPerRow
        else blocksPerRow = 12

        rConnection.eval("attr(spots, \"blocksPerRow\") <- ${blocksPerRow}")
        rConnection.eval("attr(spots, \"title\") <- \"${slideInstance.toString()}\"")
        rConnection.eval("attr(spots, \"antibody\") <- \"${slideInstance.antibody.toString()}\"")

        return rConnection
    }

    def plotInR(def rConnection, ArrayList<String> commands, Integer width, Integer height){

        rConnection.assign("fileName", "plot.png")
        rConnection.eval("png(fileName, width = ${width?:800}, height = ${height?:800})")

        commands.each{ command ->
            def r = rConnection.eval(command)
        }
        rConnection.eval("dev.off()")

        BufferedImage img
        img = rconnectService.transferPlot(rConnection, "plot.png", img)

        return(img)
    }
}
