package com.fox.one.ex.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 订单信息
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-24
 */
data class TradeOrderInfo(
    /**
     * 订单ID
     */
    @SerializedName("id") val id: String,
    /**
     * 订单时间
     */
    @SerializedName("time") val createdAt: Long,
    /**
     * 订单类型: LIMIT, MARKET
     */
    @SerializedName("type") val type: String,
    /**
     * 交易币对符号， e.g., CNBUSDT
     */
    @SerializedName("symbol") val symbol: String,
    /**
     * 交易方向，ASK，BID
     */
    @SerializedName("side") val side: String,
    /**
     * 交易价格
     */
    @SerializedName("price") val price: Double,
    /**
     * 交易状态，DONE，PENDING
     */
    @SerializedName("state") val state: String,
    /**
     * e.g., EOS/BTC: EOS未成交量
     */
    @SerializedName("remaining_amount") val remainAmount: Double,
    /**
     * e.g., EOS/BTC: EOS已成交量
     */
    @SerializedName("filled_amount") val filledAmount: Double,
    /**
     * e.g., EOS/BTC: BTC未成交量
     */
    @SerializedName("remaining_funds") val remainFunds: Double,
    /**
     * e.g., EOS/BTC: BTC已成交量
     */
    @SerializedName("filled_funds") val filledFunds: Double): Serializable {
    constructor(): this("", 0L, "", "", "", 0.0, "", 0.0, 0.0, 0.0, 0.0)

}