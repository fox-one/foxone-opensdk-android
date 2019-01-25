package com.fox.one.ex.core

import com.fox.one.ex.core.model.*
import com.fox.one.pay.core.model.AssetInfo
import com.fox.one.support.framework.network.FoxCall
import retrofit2.http.*

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-23
 */

interface IExchangeAPI {

    /**
     * 获取交易所所有币对信息
     */
    @GET("/exchange/market/pairs")
    fun getPairs(): FoxCall<List<CoinPairInfo>>

    @GET("/exchange/market/assets")
    fun getAssets(): FoxCall<List<AssetInfo.AssetBasic>>

    /**
     * 获取币对K线数据
     * @param symbol etc: EOSUSD
     * @param interval [1m|5m|15m|1h|6h|1d]
     * @param from millisecond
     * @param to millisecond
     * @param limit max items(default 1000)
     */
    @GET("/exchange/kline")
    fun getKLine(@Query("symbol") symbol: String?,
                 @Query("interval") interval: String?,
                 @Query("from") from: Long,
                 @Query("to") to: Long,
                 @Query("limit") limit: Int): FoxCall<List<KLineInfo>>

    /**
     * 获取币对交易历史
     */
    @GET("/exchange/trades")
    fun getTradeHistory(@Query("symbol") symbol: String?,
                        @Query("limit") limit: Int): FoxCall<List<TradeHistoryInfo>>

    /**
     * 获取深度信息
     */
    @GET("/exchange/depth")
    fun getDepth(@Query("symbol") symbol: String?,
                 @Query("limit") limit: Int): FoxCall<DepthResponse>

    /**
     * 获取24小时ticker数据
     */
    @GET("/exchange/ticker/24h")
    fun get24Ticker(@Query("symbol") symbol: String?): FoxCall<TickerInfo>

    /**
     * 获取所有币对的ticker数据
     */
    @GET("/exchange/ticker/24h")
    fun get24AllTickers(): FoxCall<TickersResponse>

    /**
     * 下单
     */
    @POST("/member/exchange/order")
    fun placeOrder(@Body request: PlaceOrderReqBody): FoxCall<OrderOpResponse>

    /**
     * 撤单
     */
    @DELETE("/member/exchange/order/{orderId}")
    fun cancelOrder(@Path("orderId") orderId: String): FoxCall<OrderOpResponse>

    /**
     * 获取订单列表，倒序获取订单数据，最新的订单放在第一条
     * @param symbol etc: BTCUSDT
     * @param status PENDING or DONE
     * @param fromId 从哪条ID开始获取订单列表
     * @param limit 最大请求数据量(分页用)
     */
    @GET("/member/exchange/orders")
    fun getOrders(@Query("symbol") symbol: String?, @Query("state") state: String?, @Query("cursor") cursor: String? = null, @Query("limit") limit: Int = 20): FoxCall<TradeOrderResponse>

    @GET("/member/exchange/order/{orderId}")
    fun getOrderInfo(orderId: String): FoxCall<TradeOrderInfo>

    @GET("/member/exchange/order/{orderId}/trades")
    fun getTradeInfoOfOrder(orderId: String): FoxCall<List<TradeHistoryInfo>>
}