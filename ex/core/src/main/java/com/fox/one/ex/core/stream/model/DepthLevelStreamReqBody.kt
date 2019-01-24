package com.fox.one.ex.core.stream.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-17
 */
class DepthLevelStreamReqBody(
    subId: String,
    action: String,
    @SerializedName("symbol") var symbol: String,
    @SerializedName("level") var level: Int = 5
) :
    BaseStreamReqBody(subId, action, StreamEvent.DEPTH_WITH_LEVEL) {

    companion object {
        const val LEVEL_5 = 5
        const val LEVEL_10 = 10
        const val LEVEL_20 = 20
    }
}