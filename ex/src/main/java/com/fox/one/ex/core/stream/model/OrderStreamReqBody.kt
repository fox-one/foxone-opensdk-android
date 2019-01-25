package com.fox.one.ex.core.stream.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-17
 */
class OrderStreamReqBody(
    subId: String,
    action: String,
    @SerializedName("key") val key: String
) :
    BaseStreamReqBody(subId, action, StreamEvent.USER_DATA) {
}