package com.fox.one.support.framework

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-07-12
 */
enum class Enviroment(val value: Int) {
    RELEASE(0),
    BETA(1),
    ALPHA(2);

    companion object {
        fun valueOf(value: Int): Enviroment {
            return when(value) {
                0 -> RELEASE
                1 -> BETA
                2 -> ALPHA
                else -> RELEASE
            }
        }
    }
}