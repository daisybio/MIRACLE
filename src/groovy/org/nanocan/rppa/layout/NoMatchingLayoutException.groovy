package org.nanocan.rppa.layout

/**
 * Created by IntelliJ IDEA.
 * User: mlist
 * Date: 20-06-12
 * Time: 10:57
 */

class NoMatchingLayoutException extends Exception{

    def obj

    NoMatchingLayoutException(obj)
    {
        super()
        this.obj = obj
    }
}
