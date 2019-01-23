package com.fox.one.ex.core.model

import android.text.TextUtils
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-29
 */
class DepthInfo: ArrayList<Double>() {

    fun price(): Double {
        if (size > INDEX_PRICE) {
            return get(INDEX_PRICE)
        }

        return 0.0
    }

    fun amount(): Double {
        if (size > INDEX_AMOUNT) {
            return get(INDEX_AMOUNT)
        }

        return 0.0
    }

    companion object {
        const val INDEX_PRICE = 0
        const val INDEX_AMOUNT = 1
    }
}