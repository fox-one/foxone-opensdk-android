package com.fox.one.support.framework.theme

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-02-13
 */

open class ThemeAttrSet {
    private val attrSet: MutableMap<Int, Any> = mutableMapOf()

    fun put(attr: Int, value: Any) {
        attrSet[attr] = value
    }

    fun get(attr: Int): Any {
        return attrSet[attr] ?: ""
    }

    fun getAsInt(attr: Int): Int {
        return get(attr) as Int
    }

    fun getAsLong(attr: Int): Long {
        return get(attr) as Long
    }

    fun getAsFloat(attr: Int): Float {
        return get(attr) as Float
    }

    fun getAsBoolean(attr: Int): Boolean {
        return get(attr) as Boolean
    }

    fun getAsString(attr: Int): String {
        return get(attr) as String
    }
}