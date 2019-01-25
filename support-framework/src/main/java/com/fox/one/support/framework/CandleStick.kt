package com.fox.one.support.framework

/**
 * 蜡烛图样式
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-03
 */
enum class CandleStick {
    /**
     * 绿涨红跌
     */
    GREEN_UP_RED_DOWN,
    /**
     * 红涨绿跌
     */
    RED_UP_GREEN_DOWN,
    /**
     * 灰度图，实心涨空心跌
     */
    SOLID_UP_HOLLOW_DOWN,
    /**
     * 灰度图，空心跌实心涨
     */
    HOLLOW_UP_SOLID_DOWN
}