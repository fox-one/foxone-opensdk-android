package com.fox.one.support.framework.network

import com.fox.one.support.common.utils.IOUtils
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.*
import okio.ByteString
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created by zhangyinghao on 2018/8/4.
 */
class WebSocketEngine(var url: String, var httpClient: OkHttpClient, var webSocketListener: WebSocketListener) {
    constructor(url: String, httpClient: OkHttpClient): this(url, httpClient, WSListener())
    constructor(url: String): this(url, OkHttpClient(), WSListener())

    private var websocket: WebSocket? = null

    @Synchronized
    fun connect() {
        if (websocket == null) {
            val request = Request.Builder().url(url).build()
            websocket = httpClient.newWebSocket(request, webSocketListener)
        }
    }

    @Synchronized
    fun close() {
        websocket?.cancel()
        websocket = null
    }

    fun send(message: String) {
        websocket?.send(message)
    }

    fun send(message: ByteString) {
        websocket?.send(message)
    }

    class WSListener: WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {

            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            }
    }

    enum class State {
        IDLE,
        OPENING,
        OPENED,
        CLOSING,
        CLOSED,
    }
}

fun ByteArray.unGzip(): ByteArray {
    val input = ByteArrayInputStream(this)
    val gzipInput = GzipCompressorInputStream(input)
    val buffer = ByteArray(1024)
    val output = ByteArrayOutputStream()
    var num = gzipInput.read(buffer, 0, buffer.size)
    while (num != -1) {
        output.write(buffer, 0, num)
        num = gzipInput.read(buffer, 0, buffer.size)
    }
    val byteArray = output.toByteArray()
    output.flush()
    IOUtils.closeQuietly(output)
    IOUtils.closeQuietly(gzipInput)
    IOUtils.closeQuietly(input)
    return byteArray
}