package com.fox.one.ex.core.stream.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
data class TickerStreamInfo(
                       @SerializedName("s") var symbol: String,
                       @SerializedName("c") var price: Double,
                       @SerializedName("p") var change: Double,
                       @SerializedName("P") var changePercent: Double,
                       @SerializedName("o") var openTime: Long,
                       @SerializedName("O") var open: Double,
                       @SerializedName("h") var high: Double,
                       @SerializedName("l") var low: Double,
                       @SerializedName("v") var volume: Double,
                       @SerializedName("V") var quoteVolume: Double): BaseStreamInfo {
}