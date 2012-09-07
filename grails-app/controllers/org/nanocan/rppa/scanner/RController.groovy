package org.nanocan.rppa.scanner

import rconnect.RconnectService
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class RController {

    def rconnectService
    def roperationsService

    def plotHeatmap(){

        def slideInstance = Slide.get(params.id)

        def rConnection = rconnectService.getConnection()
        def tempDir = roperationsService.createTempDir(rConnection)

        roperationsService.setWorkingDirectory(rConnection, tempDir)

        rConnection.eval("rm(list=ls())")
        rConnection.eval("require(rppa)")

        rConnection.eval("ls()")
        rConnection.eval("spots <- rppa.load(baseUrl=\"http://192.168.56.101:8080/MIRACLE/spotExport/\", slideIndex=${slideInstance.id})")
        rConnection.eval("spots <- spots[,c(\"Block\", \"Row\", \"Column\", \"Signal\", \"Deposition\", \"DilutionFactor\")]")
        if (slideInstance.layout.blocksPerRow) rConnection.eval("attr(spots, \"blocksPerRow\") <- ${slideInstance.layout.blocksPerRow}")

        rConnection.assign("fileName", "heatmap.png")

        rConnection.eval("png(fileName, width = 800, height = 800)")
        rConnection.eval("rppa.plot.heatmap(spots, plotNA=F, title=\"${slideInstance.toString()}\")")
        rConnection.eval("dev.off()")

        BufferedImage img
        img = rconnectService.transferPlot(rConnection, "heatmap.png", img)

        response.contentType = 'image/png' // or the appropriate image content type

        ImageIO.write(img, "png", response.getOutputStream())

        response.outputStream.flush()
        response.outputStream.close()

        img.close()
        rConnection.close()

        render(response)
    }
}
