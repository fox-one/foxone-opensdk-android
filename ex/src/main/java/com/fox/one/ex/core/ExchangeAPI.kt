package com.fox.one.ex.core

import android.text.TextUtils
import com.fox.one.ex.core.model.*
import com.fox.one.pay.core.model.AssetInfo
import com.fox.one.support.framework.Order
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.FoxCall
import com.fox.one.support.framework.network.HttpEngine
import com.google.gson.JsonElement
import okhttp3.OkHttpClient

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-23
 */
object ExchangeAPI: IExchangeAPI {

    override fun getPairs(): FoxCall<List<CoinPairInfo>> {
        return CloudAPI.Impl.getPairs()
    }

    override fun getAssets(): FoxCall<List<AssetInfo>> {
        return apiloader.load(IExchangeAPI::class.java)
            .getAssets()
    }

    override fun getKLine(symbol: String?, interval: String?, from: Long, to: Long, limit: Int): FoxCall<List<KLineInfo>> {
        return apiloader.load(IExchangeAPI::class.java)
            .getKLine(symbol, interval, from, to, limit)
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

    override fun getOrders(symbol: String?, state: String?, cursor: String?, limit: Int, order: String): FoxCall<TradeOrderResponse> {
        return apiloader.load(IExchangeAPI::class.java)
            .getOrders(symbol, state, cursor, limit, if (TextUtils.isEmpty(order)) Order.DESC.name else order)
    }

    override fun getOrderInfo(orderId: String): FoxCall<TradeOrderInfo> {
        return apiloader.load(IExchangeAPI::class.java)
            .getOrderInfo(orderId)
    }

    override fun getTradeInfoOfOrder(orderId: String): FoxCall<List<TradeHistoryInfo>> {
        return apiloader.load(IExchangeAPI::class.java)
            .getTradeInfoOfOrder(orderId)
    }

    override fun getAssetIntro(assetId: String): FoxCall<AssetIntroResponse> {
        return apiloader.load(IExchangeAPI::class.java)
            .getAssetIntro(assetId)
    }

    private val apiloader = APILoader()

    const val ALPHA_URL = "https://dev-gateway.fox.one"
    const val BETA_URL = "https://openapi.fox.one"
    const val RELEASE_URL = "https://openapi.fox.one"

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpEngine.defaultInterceptor)
            .build()

        apiloader.setOkHttp(okHttpClient)
        apiloader.setBaseUri(APILoader.BaseUrl(ALPHA_URL, BETA_URL, RELEASE_URL))
    }
}