package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-07
 */
data class CreateAPIKeyReqBody(@SerializedName("label") var label: String,
                               @SerializedName("ip") var ips: List<String>?): Serializable {
    init {
        val ipSize = ips?.size ?: 0
        if (ipSize > 3) {
            ips = ips?.subList(0, 3)
        }
    }
}