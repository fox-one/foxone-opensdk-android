package com.fox.one.ex.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-29
 */
data class DepthResponse(@SerializedName("last_update_id") var lastUpdateId: Long,
                         @SerializedName("bids") var bids: List<DepthInfo>,
                         @SerializedName("asks") var asks: List<DepthInfo>): Serializable