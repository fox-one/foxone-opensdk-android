package com.fox.one.ex.core.stream

import com.fox.one.ex.core.stream.model.AllTickerStreamInfo
import com.fox.one.ex.core.stream.model.AllTickerStreamReqBody


/**
 * 所有Ticker数据流监听器
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
abstract class AllTickerStreamObserver(reqBody: AllTickerStreamReqBody): StreamObserver<AllTickerStreamReqBody, AllTickerStreamInfo>(reqBody) {
}