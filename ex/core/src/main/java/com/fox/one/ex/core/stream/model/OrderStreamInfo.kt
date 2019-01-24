package com.fox.one.ex.core.stream.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
class OrderStreamInfo(
    marketId: String,
    symbol: String,
    price: Double,
    amount: Double,
    /**
     * side ASK or BID
     */
    @SerializedName("S") var side: String,
    @SerializedName("o") var orderId: String
) : BaseOrderStreamInfo(marketId, symbol, price, amount) {
}