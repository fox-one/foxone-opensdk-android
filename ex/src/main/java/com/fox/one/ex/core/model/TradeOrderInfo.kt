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
    @SerializedName("id") var id: String,
    /**
     * 订单时间
     */
    @SerializedName("time") var createdAt: Long,
    /**
     * 订单类型: LIMIT, MARKET
     */
    @SerializedName("type") var type: String,
    /**
     * 交易币对符号， e.g., CNBUSDT
     */
    @SerializedName("symbol") var symbol: String,
    /**
     * 交易方向，ASK，BID
     */
    @SerializedName("side") var side: String,
    /**
     * 交易价格
     */
    @SerializedName("price") var price: Double,
    /**
     * 交易状态，DONE，PENDING
     */
    @SerializedName("state") var state: String,
    /**
     * e.g., EOS/BTC: EOS未成交量
     */
    @SerializedName("remaining_amount") var remainAmount: Double,
    /**
     * e.g., EOS/BTC: EOS已成交量
     */
    @SerializedName("filled_amount") var filledAmount: Double,
    /**
     * e.g., EOS/BTC: BTC未成交量
     */
    @SerializedName("remaining_funds") var remainFunds: Double,
    /**
     * e.g., EOS/BTC: BTC已成交量
     */
    @SerializedName("filled_funds") var filledFunds: Double): Serializable {
    constructor(): this("", 0L, "", "", "", 0.0, "", 0.0, 0.0, 0.0, 0.0)

}