package com.fox.one.pay.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *
 * {
        timestamp: 1547812800000,
        from: "BTC",
        to: "CNY",
        price: "24445.04197737",
        changeIn24h: "0.0012956246607303"
    }
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-30
 */
data class CurrencyTicker(
    /**
     * timestamp
     */
    @SerializedName("timestamp") var timestamp: Long,
    /**
     * 汇率原始币
     */
    @SerializedName("from") var from: String,
    /**
     * 汇率目标币
     */
    @SerializedName("to") var to: String,
    /**
     * 价格
     */
    @SerializedName("price") var price: Double,
    /**
     * 24小时变化量
     */
    @SerializedName("changeIn24") var changeIn24: Double
) : Serializable {
    constructor() : this(0L, "", "", 0.0, 0.0)
}