package com.fox.one.ex.core.stream.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
data class TradeStreamInfo(
                           @SerializedName("m") var marketId: String,
                           @SerializedName("s") var symbol: String,
                           @SerializedName("id") var tradeId: String,
                           @SerializedName("p") var price: Double,
                           @SerializedName("a") var amount: Double,
                           /**
                            * side: ASK or BID
                            */
                           @SerializedName("S") var side: String): BaseStreamInfo {
}