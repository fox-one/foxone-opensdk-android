package com.fox.one.ex.core.stream

import com.fox.one.ex.core.stream.model.TickerStreamInfo
import com.fox.one.ex.core.stream.model.TickerStreamReqBody


/**
 * Ticker数据流监听器
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
abstract class TickerStreamObserver(reqBody: TickerStreamReqBody): StreamObserver<TickerStreamReqBody, TickerStreamInfo>(reqBody) {
}