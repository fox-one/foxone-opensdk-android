package com.fox.one.ex.core.stream.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
open class BaseOrderStreamInfo: BaseStreamInfo {

    @SerializedName("m")
    val marketId: String = ""

    @SerializedName("s")
    val symbol: String = ""

    @SerializedName("p")
    val price: Double = 0.0

    @SerializedName("a")
    val amount: Double = 0.0

    @SerializedName("S")
    val side: String = "ASK" //order side: ASK,BID
    @SerializedName("o")
    val order: RawOrderInfo = RawOrderInfo()

    data class RawOrderInfo(@SerializedName("id") var orderId: String,
                            @SerializedName("ts") var createTime: Long,
                            /**
                             * see LIMIT, MARKET
                             */
                            @SerializedName("t") var orderType: String,
                            @SerializedName("m") var marketId: String,
                            @SerializedName("s") var symbol: String,
                            @SerializedName("S") var orderSide: String,
                            @SerializedName("p") var price: Double,
                            /**
                             * see PENDING, DONE
                             */
                            @SerializedName("x") var state: String,
                            @SerializedName("r") var remainingAmount: Double,
                            @SerializedName("f") var filledAmount: Double,
                            @SerializedName("R") var remainingFunds: Double,
                            @SerializedName("F") var filledFunds: Double): Serializable {
        constructor(): this("", 0L, "", "", "", "", 0.0, "", 0.0, 0.0, 0.0, 0.0)
    }
}