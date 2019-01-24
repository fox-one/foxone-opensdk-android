package com.fox.one.pay.core.rate

import com.fox.one.support.framework.network.FoxCall
import retrofit2.http.GET

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-28
 */
interface CurrencyRateAPI {

    @GET("https://api.gbi.news/currency")
    fun getCurrencyRates(): FoxCall<CurrencyResponse>
}