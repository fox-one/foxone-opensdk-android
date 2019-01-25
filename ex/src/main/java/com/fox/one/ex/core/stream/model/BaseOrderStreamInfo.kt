package com.fox.one.ex.core.stream.model

import com.google.gson.annotations.SerializedName

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
open class BaseOrderStreamInfo(
    @SerializedName("m") var marketId: String,
    @SerializedName("s") var symbol: String,
    @SerializedName("p") var price: Double,
    @SerializedName("a") var amount: Double
) : BaseStreamInfo {
}