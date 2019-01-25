package com.fox.one.ex.core.stream.model

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-17
 */
enum class StreamAction(key: String) {
    SUB("sub"),
    UNSUB("unsub");

    var key: String = key
}