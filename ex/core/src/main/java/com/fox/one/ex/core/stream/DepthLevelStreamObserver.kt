package com.fox.one.ex.core.stream

import com.fox.one.ex.core.stream.model.DepthLevelStreamInfo
import com.fox.one.ex.core.stream.model.DepthLevelStreamReqBody


/**
 * 深度数据流监听器
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
abstract class DepthLevelStreamObserver(reqBody: DepthLevelStreamReqBody): StreamObserver<DepthLevelStreamReqBody, DepthLevelStreamInfo>(reqBody) {
}