package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-02-26
 */
data class TFAResponse(@SerializedName("qrcode") val qrcode: String? = "",
                       @SerializedName("secret") val secret: String? = ""): Serializable {
    constructor(): this("", "")
}