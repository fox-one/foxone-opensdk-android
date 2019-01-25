package com.fox.one.support.common.extension

import android.app.Activity

/**
 * Created by zhangyinghao on 2018/6/29.
 */

fun Activity.restoreBackgroundDark() {
    val layoutParams = this.window.attributes
    layoutParams.alpha = 1f
    this.window.attributes = layoutParams
}

fun Activity.makeBackgroundDark() {
    val attributes = this.window.attributes
    attributes.alpha = 0.5f
    this.window.attributes = attributes
}