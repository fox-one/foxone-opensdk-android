package com.fox.one.support.framework.theme

import android.content.Context
import android.content.res.TypedArray
import com.fox.one.support.common.utils.LogUtils

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-02-13
 */
object ThemeManager {

    private val attrs = mutableListOf<Int>()
    private val parseListeners = mutableListOf<OnParseListener>()

    private var isParseSuccess = false

    fun onActivityCreate(context: Context) {

        if (!isParseSuccess) {
            parseTheme(context)
        }
    }

    fun addAttr(attrResId: Int) {
        attrs.add(attrResId)
    }

    fun addAttrs(attrResIds: Collection<Int>) {
        attrs.addAll(attrResIds)
    }

    fun addParseListener(listener: OnParseListener) {
        parseListeners.add(listener)
    }

    private fun parseTheme(context: Context) {
        val typedArray = context.obtainStyledAttributes(attrs.toIntArray())
        val indexCount = typedArray.indexCount
        LogUtils.i("foxone", "theme.attr.count:$indexCount")
        for (i in 0..(indexCount - 1)) {
            val attrIndex = typedArray.getIndex(i)
            val attr = attrs[attrIndex]

            parseListeners.forEach {
                it.onParse(typedArray, attr, attrIndex)
            }
        }

        typedArray.recycle()

        if (indexCount > 0) {
            isParseSuccess = true
        }
    }

    interface OnParseListener {
        fun onParse(typedArray: TypedArray, attr: Int, index: Int)
    }
}