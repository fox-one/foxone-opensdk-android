package com.fox.one.ex.core

import com.fox.one.ex.core.model.*
import com.fox.one.pay.core.model.AssetInfo
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.FoxCall
import com.fox.one.support.framework.network.HttpEngine
import okhttp3.OkHttpClient

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-23
 */
object ExchangeAPI: IExchangeAPI {
    override fun getPairs(): FoxCall<List<CoinPairInfo>> {
        return apiLoader.load(IExchangeAPI::class.java)
            .getPairs()
    }

    override fun getAssets(): FoxCall<List<AssetInfo.AssetBasic>> {
        return apiloader.load(IExchangeAPI::class.java)
            .getAssets()
    }

    override fun getKLine(symbol: String?, interval: String?, from: Long, limit: Int): FoxCall<List<KLineInfo>> {
        return apiloader.load(IExchangeAPI::class.java)
            .getKLine(symbol, interval, from, limit)
    }

    override fun getTradeHistory(symbol: String?, limit: Int): FoxCall<List<TradeHistoryInfo>> {
        return apiloader.load(IExchangeAPI::class.java)
            .getTradeHistory(symbol, limit)
    }

    override fun getDepth(symbol: String?, limit: Int): FoxCall<DepthResponse> {
        return apiloader.load(IExchangeAPI::class.java)
            .getDepth(symbol, limit)
    }

    override fun get24Ticker(symbol: String?): FoxCall<TickerInfo> {
        return apiloader.load(IExchangeAPI::class.java)
            .get24Ticker(symbol)
    }

    override fun get24AllTickers(): FoxCall<TickersResponse> {
        return apiloader.load(IExchangeAPI::class.java)
            .get24AllTickers()
    }

    override fun placeOrder(request: PlaceOrderReqBody): FoxCall<OrderOpResponse> {
        return apiloader.load(IExchangeAPI::class.java)
            .placeOrder(request)
    }

    override fun cancelOrder(orderId: String): FoxCall<OrderOpResponse> {
        return apiloader.load(IExchangeAPI::class.java)
            .cancelOrder(orderId)
    }

    override fun getOrders(symbol: String?, state: String?, cursor: String?, limit: Int): FoxCall<TradeOrderResponse> {
        return apiloader.load(IExchangeAPI::class.java)
            .getOrders(symbol, state, cursor, limit)
    }

    override fun getOrderInfo(orderId: String): FoxCall<TradeOrderInfo> {
        return apiloader.load(IExchangeAPI::class.java)
            .getOrderInfo(orderId)
    }

    override fun getTradeInfoOfOrder(orderId: String): FoxCall<List<TradeHistoryInfo>> {
        return apiloader.load(IExchangeAPI::class.java)
            .getTradeInfoOfOrder(orderId)
    }

    private val apiloader = APILoader()

    const val ALPHA_URL = "https://dev-gateway.fox.one"
    const val BETA_URL = "https://gateway.fox.one"
    const val RELEASE_URL = "https://gateway.fox.one"

    var apiLoader = APILoader()

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpEngine.defaultInterceptor)
            .build()

        apiLoader.setOkHttp(okHttpClient)
        apiLoader.setBaseUri(APILoader.BaseUrl(ALPHA_URL, BETA_URL, RELEASE_URL))
    }
}