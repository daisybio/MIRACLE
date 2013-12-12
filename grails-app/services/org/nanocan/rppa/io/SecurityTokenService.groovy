package org.nanocan.rppa.io

import org.nanocan.rppa.scanner.Slide

class SecurityTokenService {

    def getSecurityToken(Slide slide) {
        if(!slide?.uuid){
            slide.uuid = UUID.randomUUID().toString()
            slide.save(flush:true)
        }

        return slide.uuid
    }
}
