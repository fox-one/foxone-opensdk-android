package com.fox.one.support.framework.delegate

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-07-10
 */
interface IDelegate<T: Delegable> {

    fun setDelegable(obj: T)
}