package com.fox.one.ex.core.stream.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
data class KLineStreamInfo(
                      @SerializedName("s") var symbol: String,
                      @SerializedName("t") var openTime: Long,
                      @SerializedName("o") var open: Double,
                      @SerializedName("h") var high: Double,
                      @SerializedName("l") var low: Double,
                      @SerializedName("c") var current: Double,
                      @SerializedName("v") var volume: Double,
                      @SerializedName("V") var quoVolume: Double,
                      @SerializedName("i") var intervar: String): BaseStreamInfo {
}