package com.fox.one.support.common.extension

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

/**
 * Created by zhangyinghao on 2018/6/27.
 */

fun <T : ViewModel> FragmentActivity.viewModelOf(t: Class<T>): T {
    return ViewModelProviders.of(this).get(t)
}

fun <T : ViewModel> Fragment.viewModelOf(t: Class<T>): T? {
    return this.activity?.let { ViewModelProviders.of(it).get(t) }
}

fun <T : ViewModel> FragmentActivity.viewModelWithoutCacheOf(t: Class<T>): T {
    return ViewModelProviders.of(this).get(t)
}

fun <T : ViewModel> Fragment.viewModelWithoutCacheOf(t: Class<T>): T {
    return ViewModelProviders.of(this).get(t)
}