package com.fox.one.ex.core.model

import android.text.TextUtils
import com.fox.one.support.common.utils.NumberUtils

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-29
 */
class KLineInfo : ArrayList<String>() {

    fun openTime(): Long {
        if (size > INDEX_OPEN_TIME && !TextUtils.isEmpty(get(INDEX_OPEN_TIME))) {
            return NumberUtils.toLong(get(INDEX_OPEN_TIME))
        }

        return 0L
    }

    fun open(): Double {
        if (size > INDEX_OPEN && !TextUtils.isEmpty(get(INDEX_OPEN))) {
            return NumberUtils.toDouble(get(INDEX_OPEN))
        }

        return 0.0
    }

    fun high(): Double {
        if (size > INDEX_HIGH && !TextUtils.isEmpty(get(INDEX_HIGH))) {
            return NumberUtils.toDouble(get(INDEX_HIGH))
        }

        return 0.0
    }

    fun low(): Double {
        if (size > INDEX_LOW && !TextUtils.isEmpty(get(INDEX_LOW))) {
            return NumberUtils.toDouble(get(INDEX_LOW))
        }

        return 0.0
    }

    fun close(): Double {
        if (size > INDEX_CLOSE && !TextUtils.isEmpty(get(INDEX_CLOSE))) {
            return NumberUtils.toDouble(get(INDEX_CLOSE))
        }

        return 0.0
    }

    fun volume(): Double {
        if (size > INDEX_VOLUME && !TextUtils.isEmpty(get(INDEX_VOLUME))) {
            return NumberUtils.toDouble(get(INDEX_VOLUME))
        }

        return 0.0
    }

    companion object {
        const val INDEX_OPEN_TIME = 0
        const val INDEX_OPEN = 1
        const val INDEX_HIGH = 2
        const val INDEX_LOW = 3
        const val INDEX_CLOSE = 4
        const val INDEX_VOLUME = 5

        const val INTERVAL_1M = "1m"
        const val INTERVAL_5M = "5m"
        const val INTERVAL_15M = "15m"
        const val INTERVAL_1H = "1h"
        const val INTERVAL_6H = "6h"
        const val INTERVAL_1d = "1d"
    }
}