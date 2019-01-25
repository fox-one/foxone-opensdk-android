package com.fox.one.ex.core.stream.model

import com.fox.one.ex.core.model.DepthInfo
import com.google.gson.annotations.SerializedName

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
data class DepthLevelStreamInfo(
    @SerializedName("m") var marketId: String,
    @SerializedName("s") var symbol: String,
    @SerializedName("u") var lastId: Long,
    @SerializedName("b") var bids: List<DepthInfo>,
    @SerializedName("a") var asks: List<DepthInfo>): BaseStreamInfo {
}