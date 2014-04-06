/*
 * Copyright (C) 2014
 * Center for Excellence in Nanomedicine (NanoCAN)
 * Molecular Oncology
 * University of Southern Denmark
 * ###############################################
 * Written by:	Markus List
 * Contact: 	mlist'at'health'.'sdu'.'dk
 * Web:			http://www.nanocan.org/miracle/
 * ###########################################################################
 *	
 *	This file is part of MIRACLE.
 *
 *  MIRACLE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with this program. It can be found at the root of the project page.
 *	If not, see <http://www.gnu.org/licenses/>.
 *
 * ############################################################################
 */
package org.nanocan.rppa.scanner

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import grails.plugins.springsecurity.Secured
import org.apache.commons.codec.binary.Base64

@Secured(['ROLE_USER'])
class RController {

    def rppaService
    def rconnectService
    def grailsApplication

    public final List layoutProperties = ["SampleName","SampleType", "TargetGene", "CellLine", "LysisBuffer", "Deposition", "Inducer", "Treatment"]

    @Secured(['ROLE_ADMIN'])
    def installPackageToR(){
        def rConnection = rconnectService.getConnection()
        rConnection.voidEval("download.file(\"${grailsApplication.config.grails.serverURL}/rlib/rppa_1.0.tar.gz\", \"rppa.tar.gz\")")
        rConnection.voidEval("install.packages(\"rppa.tar.gz\", repos = NULL, type = \"source\")")
        render(rConnection.eval("require(Rmiracle)").toDebugString())
    }

    def heatmap(){
        [slideId: params.id]
    }

    def plotHeatmap(){

        def images = plot(params.id as Integer, [["rppa.plot.heatmap(spots, title=title)"]])

        [images: images]
    }

    def proteinConcentration(){
        [id: params.id, layoutProperties: layoutProperties]
    }

    def plotProteinConcentration(){

        def stringenize = { return (it.collect{ x -> ("\"" + x.toString() + "\"")})}
        def vectorize = { String s = stringenize(it).toString(); return("c(" + s.substring(1, s.length()-1) + ")")}
        def method = params.method

        def sample = params.sample?vectorize(params.list("sample")):"NA"
        def A = params.A?vectorize(params.list("A")):"NA"
        def B = params.B?vectorize(params.list("B")):"NA"
        def fill = params.fill?vectorize(params.list("fill")):"NA"

        def images
        def quantCommand
        if(method == "SuperCurve") quantCommand = "result <- rppa.superCurve(spots, select.columns.sample=${sample}, select.columns.A=${A}, select.columns.B=${B}, select.columns.fill=${fill}, interactive=F)"
        else quantCommand = "result <- rppa.serialDilution(spots, select.columns.sample=${sample}, select.columns.A=${A}, select.columns.B=${B}, select.columns.fill=${fill})"

        images = plot(params.id as Integer, [
                [
                    "spots\$DilutionFactor <- as.double(spots\$DilutionFactor)",
                    quantCommand
                ],
                [   "rppa.proteinConc.plot(result, error.bars=F, horizontal.line=F)"    ]
            ])


        [images: images]
    }

    def error(){
        [:]
    }

    private plot(int slideId, List<ArrayList<String>> commands){

        def rConnection = rppaService.openConnectionAndTransferSlide(slideId)

        def images = commands.collect
        {
            BufferedImage img
            img = rppaService.plotInR(rConnection, it, null, null)
            ByteArrayOutputStream baos = new ByteArrayOutputStream()
            ImageIO.write(img, "png", baos)
            baos.flush()
            String encodeImage = Base64.encodeBase64String(baos.toByteArray())
            baos.close()

            return(encodeImage)
        }

        rConnection.close()

        return(images)
    }
}
