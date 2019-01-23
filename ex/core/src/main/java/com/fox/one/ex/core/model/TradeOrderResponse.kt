package com.fox.one.ex.core.model

import com.google.gson.annotations.SerializedName

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-07
 */
class TradeOrderResponse: ExBaseResponse() {

    @SerializedName("pagination")
    var pagination: Pagination? = null

    @SerializedName("orders")
    var orders: List<TradeOrderInfo>? = emptyList()
}