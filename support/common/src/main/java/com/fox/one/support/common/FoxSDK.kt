package com.fox.one.support.common

import android.app.Application

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-19
 */
object FoxSDK {

    var merchantID : String = ""

    fun init(application: Application, merchaintId: String) {
        this.merchantID = merchantID
    }
}