package com.fox.one.ex.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-29
 */
data class TradeHistoryInfo(
    /**
     * 交易ID
     */
    @SerializedName("id") var id: String,
    /**
     * 交易时间
     */
    @SerializedName("time") var time: Long = 0L,
    /**
     * 交易价格
     */
    @SerializedName("price") var price: Double = 0.0,
    /**
     * 交易额
     */
    @SerializedName("amount") var amount: Double = 0.0,
    /**
     * 交易方向
     */
    @SerializedName("side") var side: String = ""): Serializable