package com.fox.one.support.framework.network

import java.io.Serializable

/**
 * base response
 */
 abstract class BaseResponse : Serializable {
    abstract fun code(): Int
    abstract fun isSuccessful(): Boolean
}
