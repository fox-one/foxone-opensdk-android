package com.fox.one.ex.core.stream

import com.fox.one.ex.core.stream.model.BaseOrderStreamInfo
import com.fox.one.ex.core.stream.model.OrderStreamReqBody


/**
 * 订单数据流监听器
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
abstract class OrderStreamObserver(reqBody: OrderStreamReqBody): StreamObserver<OrderStreamReqBody, BaseOrderStreamInfo>(reqBody) {
}