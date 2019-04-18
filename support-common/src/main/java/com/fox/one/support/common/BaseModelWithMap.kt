package com.fox.one.support.common

import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-08-03
 */
abstract class BaseModelWithMap(map: MutableMap<String, Any?>?): Serializable {
    val defaultMap = map?.withDefault { null }
}