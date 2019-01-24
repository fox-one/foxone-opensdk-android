package com.fox.one.ex.core.stream.model

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-17
 */

class StreamResponse: Serializable {

    @SerializedName("e")
    var event: String? = null

    @SerializedName("ts")
    var timeInSecond: Long = 0L

    @SerializedName("k")
    var data: JsonElement? = null
}