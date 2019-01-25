package com.fox.one.pay.core.rate

import com.fox.one.pay.core.model.CurrencyTicker
import com.fox.one.pay.core.model.PayBaseResponse
import com.fox.one.pay.core.model.Currencies
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-30
 */
class CurrencyResponse: PayBaseResponse() {

    @SerializedName("data")
    var data: Data? = Data()

    data class Data(@SerializedName("cnyTickers") var cnyTickers: List<CurrencyTicker>): Serializable {
        constructor(): this(mutableListOf())
    }
}