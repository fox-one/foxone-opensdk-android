package com.fox.one.pay.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-30
 */
data class Currencies(
    /**
     * 美元价格
     */
    @SerializedName("usd") var usd: Double,
    /**
     * USDT价格
     */
    @SerializedName("usdt") var usdt: Double): Serializable {
    constructor(): this(0.0, 0.0)
}