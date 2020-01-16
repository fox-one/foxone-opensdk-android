package com.fox.one.support.common.utils

import android.text.TextUtils
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.abs

/**
 * Created by SimonL on 2014/11/05
 */
object NumberUtils {

    fun toInt(s: String?): Int {
        try {
            return Integer.parseInt(s)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }

    fun toLong(s: String?): Long {
        try {
            return java.lang.Long.parseLong(s)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0L
    }

    fun toFloat(s: String?): Float {
        try {
            return java.lang.Float.parseFloat(s)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0.0f
    }

    fun toDouble(s: String?, locale: Locale? = null, maxFractionDigits: Int = 8): Double {
        try {
            val format = if (locale == null) {
                NumberFormat.getInstance()
            } else {
                NumberFormat.getInstance(locale)
            }
            return format.apply {
                maximumFractionDigits = maxFractionDigits
            }
                .parse(s)
                .toDouble()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0.0
    }

    fun toBoolean(s: String?): Boolean {
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

    fun double2Float(double: Double): Float {
        var tmpDouble = double
        return if (tmpDouble < 0) {
            tmpDouble = -double
            -(Math.floor(tmpDouble * Math.pow(10.0, 7.0)) / Math.pow(10.0, 7.0)).toFloat()
        } else {
            (Math.floor(tmpDouble * Math.pow(10.0, 7.0)) / Math.pow(10.0, 7.0)).toFloat()
        }
    }
}

fun formatPhoneNumber(phoneNumber: String): String {
    if (!TextUtils.isEmpty(phoneNumber)) {
        val validPhoneNumber = StringBuilder()
        val phoneNumChar = phoneNumber.toCharArray()
        for (c in phoneNumChar) {
            if (c in '0'..'9') {
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
    if (num >= 100) return 2
    if (num >= 1 && num < 100) return 4
    var rentention = 3
    while (num < 1) {
        num *= 10.toDouble()
        rentention += 1
    }
    return rentention
}

fun Double.getRententionString(): String {
    return this.toNumberString(this.getRentention())
}

fun Double.toSeparatorString(fractionDigits: Int = 8): String {
    return DecimalFormat.getNumberInstance().apply {
        maximumFractionDigits = fractionDigits
        roundingMode = RoundingMode.DOWN
    }.format(this)
}

fun Double.toNumberString(fractionDigits: Int = 8, minFractionDigits: Int = 0): String {
    return DecimalFormat.getNumberInstance().apply {
        maximumFractionDigits = fractionDigits
        roundingMode = RoundingMode.DOWN
        isGroupingUsed = false
        minimumFractionDigits = minFractionDigits
    }.format(this)
}

fun Double.toLegalString(): String {
    var num = abs(this) * 100
    // 0 -> 0.00
    if (num == 0.0) return toNumberString(2, 2)
    var rentention = 0
    while (num < 1) {
        num *= 10.toDouble()
        rentention += 1
    }
    // 两位以内保留小数点后两位
    if (rentention == 0) {
        return toNumberString(2, 2)
    }
    // 两位之外保留3位有效数字位
    return toNumberString(rentention + 4)
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