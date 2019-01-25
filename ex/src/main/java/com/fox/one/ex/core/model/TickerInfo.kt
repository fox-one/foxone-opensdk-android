package com.fox.one.ex.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.DecimalFormat

/**
 * Ticker info
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-29
 */
data class TickerInfo(
    /**
     * 价格
     */
    @SerializedName("price") var price: Double,
    /**
     * 价格变化量
     */
    @SerializedName("price_change") var priceChange: Double,
    /**
     * 价格变化百分比
     */
    @SerializedName("price_change_percent") var priceChangePercent: Double,
    /**
     * 开盘时间
     */
    @SerializedName("open_time") var openTime: Long,
    /**
     * 开盘价
     */
    @SerializedName("open_price") var openPrice: Double,
    /**
     * 最高价
     */
    @SerializedName("high_price") var highPrice: Double,
    /**
     * 最低价
     */
    @SerializedName("low_price") var lowPrice: Double,
    /**
     * 交易量
     */
    @SerializedName("volume") var volume: Double,
    /**
     * 计价币交易量
     */
    @SerializedName("quote_volume") var quoteVolume: Double): Serializable {
    constructor(): this(0.0, 0.0, 0.0, 0L, 0.0, 0.0, 0.0, 0.0, 0.0)
}