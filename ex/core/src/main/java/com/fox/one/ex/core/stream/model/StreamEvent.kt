package com.fox.one.ex.core.stream.model

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-17
 */

object StreamEvent {
    const val ORDER_OPEN = "ORDER-OPEN"
    const val ORDER_CANCEL = "ORDER-CANCEL"
    const val ORDER_MATCH = "ORDER-MATCH"

    const val USER_DATA = "user_data"
    const val KLINE = "kline"
    const val TICKER_24HR = "24hr_ticker"
    const val ALL_TICKER_24HR = "24hr_ticker_all"
    const val DEPTH_UPDATE = "depth_update"
    const val DEPTH_WITH_LEVEL = "depth"
    const val TRADE = "trade"
}