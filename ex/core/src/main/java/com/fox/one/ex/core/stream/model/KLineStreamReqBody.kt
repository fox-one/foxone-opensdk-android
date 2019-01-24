package com.fox.one.ex.core.stream.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-17
 */
class KLineStreamReqBody(subId: String,
                              action: String,
                              @SerializedName("symbol") val symbol: String,
                              @SerializedName("interval") val interval: String):
    BaseStreamReqBody(subId, action, StreamEvent.KLINE) {
}