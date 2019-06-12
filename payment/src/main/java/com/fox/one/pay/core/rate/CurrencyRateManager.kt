package com.fox.one.pay.core.rate

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.fox.one.support.framework.Currency
import com.fox.one.pay.core.model.CurrencyTicker
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.HttpEngine
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 法币及数字货币汇率管理，所有法币及数字货币的汇率都是以人民币作为结算标准计算
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-09-20
 */
object CurrencyRateManager: CurrencyRateServerFramework() {
    private val apiLoader = APILoader()

    fun init(application: Application) {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpEngine.defaultInterceptor)
            .build()

        apiLoader.setOkHttp(okHttpClient)

        apiLoader.setBaseUri(
            APILoader.BaseUrl(
                "https://dev-gateway.fox.one",
                "https://openapi.fox.one",
                "https://openapi.fox.one"
            )
        )
        startSync()
    }

    override fun onSyncExchangeRate(listener: OnExchangeRateUpdateListener) {
        apiLoader.load(CurrencyRateAPI::class.java).getCurrencyRates()
            .enqueue(object : Callback<CurrencyResponse> {
                override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                    listener.onRateUpdated(null)
                }

                override fun onResponse(call: Call<CurrencyResponse>, response: Response<CurrencyResponse>) {
                    listener.onRateUpdated(response.body()?.data?.cnyTickers)
                }
            })
    }
}