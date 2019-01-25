package com.fox.one.support.common.utils

import android.text.TextUtils
import java.text.DecimalFormat

/**
 * Created by SimonL on 2014/11/05
 */
object NumberUtils {

    fun toInt(s: String): Int {
        try {
            return Integer.parseInt(s)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }

    fun toLong(s: String): Long {
        try {
            return java.lang.Long.parseLong(s)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0L
    }

    fun toFloat(s: String): Float {
        try {
            return java.lang.Float.parseFloat(s)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0.0f
    }

    fun toDouble(s: String): Double {
        try {
            return java.lang.Double.parseDouble(s)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0.0
    }

    fun toBoolean(s: String): Boolean {
        try {
            return java.lang.Boolean.parseBoolean(s)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    fun toString(i: Int): String {
        return i.toString()
    }

    fun toString(f: Float): String {
        return f.toString()
    }

    fun toString(b: Boolean): String {
        return b.toString()
    }

    fun toString(l: Long): String {
        return l.toString()
    }

    fun toString(d: Double): String {
        return d.toString()
    }
}

fun formatPhoneNumber(phoneNumber: String): String {
    if (!TextUtils.isEmpty(phoneNumber)) {
        val validPhoneNumber = StringBuilder()
        val phoneNumChar = phoneNumber.toCharArray()
        for (c in phoneNumChar) {
            if (c >= '0' && c <= '9') {
                validPhoneNumber.append(c)
            }
        }

        return validPhoneNumber.toString()
    }

    return phoneNumber
}

fun Double.getRentention(): Int {
    var num = Math.abs(this)
    if (num == 0.0) return 2
    if (num >=100 ) return 2
    if (num >= 1 && num < 100) return 4
    var rentention = 3
    while (num < 1) {
        num *= 10.toDouble()
        rentention += 1
    }
    return rentention
}

fun Double.getRententionString(): String {
    return String.format("%." + this.getRentention() + "f", this)
}

fun Double.toSeparatorString(): String {
    return DecimalFormat.getNumberInstance().apply { maximumFractionDigits = 8 }.format(this)
}

fun Double.toNumberString(): String {
    return DecimalFormat.getNumberInstance().apply { maximumFractionDigits = 8 }.format(this).replace(",", "")
}

fun Double.toSIString(): String {
    return if (this < 1000) {
        String.format("%.1f", this)
    } else if (this >= 1000 && this < 1000000) { // k
        String.format("%.1f", this / 1000) + "K"
    } else {//M
        String.format("%.1f", this / 1000000) + "M"
    }
}