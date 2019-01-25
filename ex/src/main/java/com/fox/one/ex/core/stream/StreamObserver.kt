package com.fox.one.ex.core.stream

import com.fox.one.ex.core.stream.model.BaseStreamInfo
import com.fox.one.ex.core.stream.model.BaseStreamReqBody


/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-18
 */
abstract class StreamObserver<out REQ: BaseStreamReqBody, in D: BaseStreamInfo>(val reqBody: REQ) {
    abstract fun onUpdate(data: D)

    fun getSubId(): String = reqBody.subId
    fun getAction(): String = reqBody.action
    fun getEvent(): String = reqBody.event
}