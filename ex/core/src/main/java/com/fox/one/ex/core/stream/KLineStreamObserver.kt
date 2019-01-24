package com.fox.one.ex.core.stream

import com.fox.one.ex.core.stream.model.KLineStreamInfo
import com.fox.one.ex.core.stream.model.KLineStreamReqBody


/**
 * K线数据流监听器
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
abstract class KLineStreamObserver(reqBody: KLineStreamReqBody): StreamObserver<KLineStreamReqBody, KLineStreamInfo>(reqBody) {
}