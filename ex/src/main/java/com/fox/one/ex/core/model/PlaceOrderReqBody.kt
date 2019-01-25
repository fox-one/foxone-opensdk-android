package com.fox.one.ex.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-28
 */
data class PlaceOrderReqBody(
    /**
     * etc: BTCUSDT
     */
    @SerializedName("symbol")
    val symbol: String,
    /**
     * ASK or BID
      */
    @SerializedName("side")
    val side: String,
    /**
     * MARKET or LIMIT
     */
    @SerializedName("type")
    val type: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("amount")
    val amount: String,
    /**
     * uuid
     */
    @SerializedName("trace_id")
    val traceId: String): Serializable