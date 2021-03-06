package com.fox.one.ex.core.stream

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import com.fox.one.ex.core.stream.model.*
import com.fox.one.support.common.concurrent.TaskScheduler
import com.fox.one.support.common.utils.JsonUtils
import com.fox.one.support.common.utils.LogUtils
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.HttpEngine
import com.fox.one.support.framework.network.WebSocketEngine
import com.fox.one.support.framework.network.unGzip
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.net.URL
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-17
 */
object StreamDataManager {

    private const val TAG = "StreamDataManager"

    private const val ALPHA_URL = "https://dev-gateway.fox.one"
    private const val BETA_URL = "https://openapi.fox.one"
    private const val RELEASE_URL = "https://openapi.fox.one"

    private const val PATH_WS = "/ws"

    private const val ALIGNMENT_DELAY = 3000

    private var alignmentTime: Long = 0
    private val handler = StreamHandler()

    private val observers: MutableMap<String, StreamObserver<*, *>> = ConcurrentHashMap()

    private val apiLoader = APILoader()
    private var webSocketEngine: WebSocketEngine?= null

    @Volatile
    private var state: WebSocketEngine.State = WebSocketEngine.State.IDLE

    private val alignmentCache:ConcurrentHashMap<String?, StreamResponse> = ConcurrentHashMap()
    private val reqCache: MutableList<String> = CopyOnWriteArrayList<String>()

    var webSocketListener = object: WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            LogUtils.i(TAG, "${response.toString()}")
            state = WebSocketEngine.State.OPENED
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            LogUtils.i(TAG, "String:: $text")
            state = WebSocketEngine.State.OPENED
            dispatchMessage(text)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            state = WebSocketEngine.State.OPENED
            bytes?.let {
                TaskScheduler.execute {
                    val byteArray = it.toByteArray().unGzip()
                    val jsonString = byteArray.toString(Charsets.UTF_8)
                    LogUtils.i(TAG, "String:: $jsonString")
                    dispatchMessage(jsonString)
                }
            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            state = WebSocketEngine.State.CLOSING
            LogUtils.i(TAG, "$code, $reason")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            state = WebSocketEngine.State.CLOSED
            LogUtils.i(TAG, "$code, $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            state = WebSocketEngine.State.CLOSED
            LogUtils.i(TAG, "${response?.toString()}, ${t.toString()}")
        }
    }

    private fun getRealUrl(): String {
        val url = URL(apiLoader.getBaseUrl())
        val host = url.host
        return "wss://$host$PATH_WS"
    }

    init {
        apiLoader.setBaseUri(APILoader.BaseUrl(ALPHA_URL, BETA_URL, RELEASE_URL))
        webSocketEngine = WebSocketEngine(getRealUrl(), apiLoader.getHttpClient(), webSocketListener)

        LogUtils.i("foxone", "websocket: ${getRealUrl()}")
    }

    fun dispatchMessage(message: String) {
        val streamResponse = JsonUtils.optFromJson(message, StreamResponse::class.java)
        streamResponse?.let {
            if (TextUtils.isEmpty(it.event)) {
                return
            }

            alignmentCache[it.event] = it
            if (System.currentTimeMillis() - alignmentTime > ALIGNMENT_DELAY) {
                handler.post {
                    alignmentCache.forEach { cacheItem ->
                        when(cacheItem.key) {
                            StreamEvent.ORDER_OPEN -> {
                                observers.forEach {entry ->
                                    if (entry.value.getEvent() == StreamEvent.USER_DATA) {
                                        val data = JsonUtils.optFrom(cacheItem.value.data, OrderOpenStreamInfo::class.java)
                                        data?.let {
                                            (entry.value as StreamObserver<OrderStreamReqBody, BaseOrderStreamInfo>).onUpdate(data)
                                        }
                                    }
                                }
                            }
                            StreamEvent.ORDER_CANCEL -> {
                                observers.forEach {entry ->
                                    if (entry.value.getEvent() == StreamEvent.USER_DATA) {
                                        val data = JsonUtils.optFrom(cacheItem.value.data, OrderCancelStreamInfo::class.java)
                                        data?.let {
                                            (entry.value as StreamObserver<OrderStreamReqBody, BaseOrderStreamInfo>).onUpdate(data)
                                        }
                                    }
                                }
                            }
                            StreamEvent.ORDER_MATCH -> {
                                observers.forEach {entry ->
                                    if (entry.value.getEvent() == StreamEvent.USER_DATA) {
                                        val data = JsonUtils.optFrom(cacheItem.value.data, OrderMatchStreamInfo::class.java)
                                        data?.let {
                                            (entry.value as StreamObserver<OrderStreamReqBody, BaseOrderStreamInfo>).onUpdate(data)
                                        }
                                    }
                                }
                            }
                            StreamEvent.KLINE -> {
                                observers.forEach {entry ->
                                    if (entry.value.getEvent() == StreamEvent.KLINE) {
                                        val data = JsonUtils.optFrom(cacheItem.value.data, KLineStreamInfo::class.java)
                                        data?.let {
                                            (entry.value as StreamObserver<KLineStreamReqBody, KLineStreamInfo>).onUpdate(data)
                                        }
                                    }
                                }
                            }
                            StreamEvent.TICKER_24HR -> {
                                observers.forEach {entry ->
                                    if (entry.value.getEvent() == StreamEvent.TICKER_24HR) {
                                        val data = JsonUtils.optFrom(cacheItem.value.data, TickerStreamInfo::class.java)
                                        data?.let {
                                            (entry.value as StreamObserver<TickerStreamReqBody, TickerStreamInfo>).onUpdate(data)
                                        }
                                    }
                                }
                            }
                            StreamEvent.ALL_TICKER_24HR -> {
                                observers.forEach {entry ->
                                    if (entry.value.getEvent() == StreamEvent.ALL_TICKER_24HR) {
                                        val data = JsonUtils.optFrom(cacheItem.value.data, AllTickerStreamInfo::class.java)
                                        data?.let {
                                            (entry.value as StreamObserver<AllTickerStreamReqBody, AllTickerStreamInfo>).onUpdate(data)
                                        }
                                    }
                                }
                            }
                            StreamEvent.DEPTH_UPDATE -> {
                                observers.forEach {entry ->
                                    if (entry.value.getEvent() == StreamEvent.DEPTH_UPDATE) {
                                        val data = JsonUtils.optFrom(cacheItem.value.data, DepthStreamInfo::class.java)
                                        data?.let {
                                            (entry.value as StreamObserver<DepthStreamReqBody, DepthStreamInfo>).onUpdate(data)
                                        }
                                    }
                                }
                            }
                            StreamEvent.DEPTH_WITH_LEVEL -> {
                                observers.forEach {entry ->
                                    if (entry.value.getEvent() == StreamEvent.DEPTH_WITH_LEVEL) {
                                        val data = JsonUtils.optFrom(cacheItem.value.data, DepthLevelStreamInfo::class.java)
                                        data?.let {
                                            (entry.value as StreamObserver<DepthLevelStreamReqBody, DepthLevelStreamInfo>).onUpdate(data)
                                        }
                                    }
                                }
                            }
                            StreamEvent.TRADE -> {
                                observers.forEach {entry ->
                                    if (entry.value.getEvent() == StreamEvent.TRADE) {
                                        val data = JsonUtils.optFrom(cacheItem.value.data, TradeStreamInfo::class.java)
                                        data?.let {
                                            (entry.value as StreamObserver<TradeStreamReqBody, TradeStreamInfo>).onUpdate(data)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                alignmentTime = System.currentTimeMillis()
            }
        }
    }

    fun reset() {
        close()
        webSocketEngine = WebSocketEngine(getRealUrl(), apiLoader.getHttpClient(), webSocketListener)
    }

    private fun close() {
        webSocketEngine?.close()

        observers.clear()
        reqCache.clear()
        handler.removeMessages(WHAT_CHECK_CONNECTION)
        handler.removeMessages(WHAT_SEND_MSG)
        handler.removeMessages(WHAT_CLOSE)

        LogUtils.i(TAG, "websocket close>>>>>>>>>>>>>>")
    }

    private fun tryConnect() {
        if (state != WebSocketEngine.State.OPENING
            && state != WebSocketEngine.State.OPENED) {
            webSocketEngine?.connect()
            LogUtils.i(TAG, "websocket connect-----------")
            state = WebSocketEngine.State.OPENING
        }
    }

    private fun doSubscribe(observer: StreamObserver<*, *>) {
        tryConnect()

        val reqStr = JsonUtils.optToJson(observer.reqBody)
        observers[observer.reqBody.subId] = observer

        LogUtils.i(TAG, "state:$state;; subMessage::$reqStr")

        if (state == WebSocketEngine.State.OPENED) {
            webSocketEngine?.send(reqStr)
        } else {
            reqCache.add(reqStr)
            handler.sendEmptyMessage(WHAT_CHECK_CONNECTION)
        }

        handler.removeMessages(WHAT_CLOSE)
    }

    private fun doUnSubscribe(subId: String, unSubReq: String) {
        LogUtils.i(TAG, "$unSubReq")

        webSocketEngine?.send(unSubReq)
        observers.remove(subId)

        if (observers.isEmpty()) {
            handler.sendEmptyMessageDelayed(WHAT_CLOSE, DELAY_CLOSE)
        }
    }

    /**
     * 订阅K线数据流
     */
    fun subscribe(observer: KLineStreamObserver) {
        doSubscribe(observer)
    }

    /**
     * 取消K线数据流订阅
     */
    fun unsubscribe(klineRequest: KLineStreamReqBody) {
        doUnSubscribe(klineRequest.subId, JsonUtils.optToJson(klineRequest))
    }

    /**
     * 订阅Ticker数据流
     */
    fun subscribe(observer: TickerStreamObserver) {
        doSubscribe(observer)
    }

    /**
     * 取消Ticker数据流订阅
     */
    fun unsubscribe(tickerRequest: TickerStreamReqBody) {
        doUnSubscribe(tickerRequest.subId, JsonUtils.optToJson(tickerRequest))
    }

    /**
     * 订阅所有币对的Ticker数据流
     */
    fun subscribe(observer: AllTickerStreamObserver) {
        doSubscribe(observer)
    }

    /**
     * 取消所有币对Ticker数据流的订阅
     */
    fun unsubscribe(allTickerRequest: AllTickerStreamReqBody) {
        doUnSubscribe(allTickerRequest.subId, JsonUtils.optToJson(allTickerRequest))
    }

    /**
     * 订阅深度数据流
     */
    fun subscribe(observer: DepthStreamObserver) {
        doSubscribe(observer)
    }

    /**
     * 取消深度数据流订阅
     */
    fun unsubscribe(depthRequest: DepthStreamReqBody) {
        doUnSubscribe(depthRequest.subId, JsonUtils.optToJson(depthRequest))
    }

    /**
     * 订阅深度数据流，level: 5, 10, 20
     */
    fun subscribe(observer: DepthLevelStreamObserver) {
        doSubscribe(observer)
    }

    /**
     * 取消深度数据流订阅
     */
    fun unsubscribe(depthLevelRequest: DepthLevelStreamReqBody) {
        doUnSubscribe(depthLevelRequest.subId, JsonUtils.optToJson(depthLevelRequest))
    }

    /**
     * 订阅交易数据流
     */
    fun subscribe(observer: TradeStreamObserver) {
        doSubscribe(observer)
    }

    /**
     * 取消交易数据流订阅
     */
    fun unsubscribe(tradeRequest: TradeStreamReqBody) {
        doUnSubscribe(tradeRequest.subId, JsonUtils.optToJson(tradeRequest))
    }

    /**
     * 订阅订单数据流
     */
    fun subscribe(observer: OrderStreamObserver) {
        doSubscribe(observer)
    }

    /**
     * 取消订单数据流订阅
     */
    fun unsubscribe(orderRequest: OrderStreamReqBody) {
        doUnSubscribe(orderRequest.subId, JsonUtils.optToJson(orderRequest))
    }

    private const val WHAT_SEND_MSG = 1
    private const val WHAT_CHECK_CONNECTION = 2
    private const val WHAT_CLOSE = 3
    private const val DELAY_CHECK = 500L
    private const val DELAY_CLOSE = 20 * 1000L
    class StreamHandler: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            val what = msg?.what ?: 0
            when(what) {
                WHAT_CHECK_CONNECTION -> {
                    if (state == WebSocketEngine.State.OPENED) {
                        reqCache.forEach {
                            LogUtils.i(TAG, "handler send message:$it")
                            webSocketEngine?.send(it)
                        }
                        reqCache.clear()
                    } else {
                        if (handler.hasMessages(WHAT_CHECK_CONNECTION)) {
                            handler.removeMessages(WHAT_CHECK_CONNECTION)
                        }
                        LogUtils.i(TAG, "handler check connection")
                        handler.sendEmptyMessageDelayed(WHAT_CHECK_CONNECTION, DELAY_CHECK)
                    }
                }
                WHAT_CLOSE -> {
                    close()
                }
            }
        }
    }
}