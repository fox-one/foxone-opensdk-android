package com.fox.one.ex.core.stream.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
open class BaseStreamReqBody(@SerializedName("id") var subId: String,
                             @SerializedName("action") var action: String,
                             @SerializedName("event") var event: String): Serializable {

}