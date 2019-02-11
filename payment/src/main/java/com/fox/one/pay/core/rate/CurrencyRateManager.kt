package com.fox.one.pay.core.rate

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.fox.one.support.framework.Currency
import com.fox.one.pay.core.model.CurrencyTicker
import com.fox.one.support.framework.network.APILoader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 法币及数字货币汇率管理，所有法币及数字货币的汇率都是以人民币作为结算标准计算
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-09-20
 */
object CurrencyRateManager {

    private const val WHAT_SYNC = 1
    private const val DELAY_RETRY = 600L
    private const val DELAY_SYNC = 10 * 1000L
    private var syncEnable = false
    private var exchangeRateHandler = ExchangeRateHandler()
    private val apiLoader = APILoader()
    private var currencyTickers: List<CurrencyTicker>? = null

    fun init(application: Application) {

        apiLoader.setBaseUri(
            APILoader.BaseUrl(
                "https://dev-gateway.fox.one",
                "https://openapi.fox.one",
                "https://openapi.fox.one"
            )
        )
        startSync()
    }

    /**
     * sync exchange rate from network
     */
    private fun syncExchangeRate(listener: OnExchangeRateUpdateListener?) {
        apiLoader.load(CurrencyRateAPI::class.java).getCurrencyRates()
            .enqueue(object : Callback<CurrencyResponse> {
                override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                    listener?.onRateUpdated(null)
                }

                override fun onResponse(call: Call<CurrencyResponse>, response: Response<CurrencyResponse>) {
                    currencyTickers = response.body()?.data?.cnyTickers

                    listener?.onRateUpdated(currencyTickers)
                }
            })
    }

    /**
     * 获取其他币对人民币的汇率，比如：symbol="USD"则返回6.85，symbol="JPY"则返回0.06108
     */
    fun getRateBaseCNY(symbol: String): Double {
        val ticker = currencyTickers?.firstOrNull { it.from == symbol }
        return ticker?.price ?: 1.0
    }

    /**
     * 获取美元兑人民币的汇率，e.g: 6.85
     */
    fun usdRate(): Double {
        return getRateBaseCNY(Currency.USD.name)
    }

    /**
     * 获取USDT兑人民币的汇率，注意 USDT非USD（美元）
     */
    fun usdtRate(): Double {
        return getRateBaseCNY(Currency.USDT.name)
    }

    /**
     * 将美元价值换算成人民币价值
     */
    fun usd2cny(usdValue: Double): Double {
        return toCNYValue(Currency.USD.name, usdValue)
    }

    fun cny2usd(cnyValue: Double): Double {
        return fromCNYValue(Currency.USD.name, cnyValue)
    }

    fun usdt2cny(usdtValue: Double): Double {
        return toCNYValue(Currency.USDT.name, usdtValue)
    }

    fun cny2usdt(cnyValue: Double): Double {
        return fromCNYValue(Currency.USDT.name, cnyValue)
    }

    fun toCNYValue(sourceSymbol: String, value: Double): Double {
        return getRateBaseCNY(sourceSymbol) * value
    }

    fun fromCNYValue(targetSymbol: String, cnyValue: Double): Double {
        return cnyValue / getRateBaseCNY(targetSymbol)
    }

    fun getLegalSymbol(currency: Currency): String = currency.name

    fun getLegalSign(currency: Currency): String = when (currency) {
        Currency.CNY -> {
            "¥"
        }
        Currency.USD -> {
            "$"
        }
        else -> {
            ""
        }
    }

    fun startSync() {
        syncEnable = true

        exchangeRateHandler.removeMessages(WHAT_SYNC)
        exchangeRateHandler.sendEmptyMessage(WHAT_SYNC)
    }

    fun stopSync() {
        syncEnable = false

        exchangeRateHandler.removeMessages(WHAT_SYNC)
    }


    internal class ExchangeRateHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (WHAT_SYNC == msg.what) {
                if (syncEnable) {
                    syncExchangeRate(object : OnExchangeRateUpdateListener {
                        override fun onRateUpdated(cnyTickers: List<CurrencyTicker>?) {
                            if (currencyTickers == null || currencyTickers?.isEmpty() == true) {
                                exchangeRateHandler.sendEmptyMessageDelayed(WHAT_SYNC, DELAY_RETRY)
                            } else {
                                exchangeRateHandler.sendEmptyMessageDelayed(WHAT_SYNC, DELAY_SYNC)
                            }
                        }
                    })
                }
            }
        }
    }

    interface OnExchangeRateUpdateListener {
        fun onRateUpdated(cnyTickers: List<CurrencyTicker>?)
    }
}