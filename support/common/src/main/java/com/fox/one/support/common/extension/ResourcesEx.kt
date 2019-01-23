package com.fox.one.support.common.extension

import android.content.res.Resources
import android.os.Build

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-07-19
 */

fun Resources.getColorCompat(color: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) getColor(color, null) else getColor(color)
}

fun Resources.getColorCompat(color: Int, theme: Resources.Theme?): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) getColor(color, theme) else getColor(color)
}