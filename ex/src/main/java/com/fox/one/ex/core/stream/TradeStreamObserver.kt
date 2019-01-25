package com.fox.one.ex.core.stream

import com.fox.one.ex.core.stream.model.TradeStreamInfo
import com.fox.one.ex.core.stream.model.TradeStreamReqBody

/**
 * 交易数据流监听器
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
abstract class TradeStreamObserver(reqBody: TradeStreamReqBody): StreamObserver<TradeStreamReqBody, TradeStreamInfo>(reqBody) {
}