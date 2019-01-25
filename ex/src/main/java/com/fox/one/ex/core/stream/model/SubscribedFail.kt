package com.fox.one.ex.core.stream.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-17
 */
data class SubscribedFail(@SerializedName("id") val id: String,
                          @SerializedName("code") val code: Long,
                          @SerializedName("msg") val msg: String): Serializable {
    constructor(): this("", 0, "")
}