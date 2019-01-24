package com.fox.one.support.framework.network

import android.support.annotation.StringRes
import android.text.TextUtils
import com.fox.one.support.common.utils.JsonUtils
import com.fox.one.support.common.utils.NetworkUtils
import com.fox.one.support.common.utils.ToastUtil
import com.fox.one.support.framework.FoxRuntime
import retrofit2.HttpException

/**
 * Created by zhangyinghao on 2018/5/5.
 */
object HttpErrorHandler {
    private var errorHintMap: MutableMap<Int, Int> = mutableMapOf()
    private var errorListener: MutableMap<Int, ErrorListener> = mutableMapOf()

    var networkErrorToast: String = ""

    fun addErrorHint(errorCode: Int, @StringRes errorHint: Int): HttpErrorHandler {
        errorHintMap[errorCode] = errorHint

        return this
    }

    fun addErrorHints(errorHintMap: MutableMap<Int, Int>) {
        this.errorHintMap = errorHintMap
    }

    fun addErrorListener(errorCode: Int, listener: ErrorListener) {
        errorListener[errorCode] = listener
    }

    fun handle(it: Throwable?): Boolean {
        return handle(it, null)
    }

    fun handle(it: Throwable?, listener: ((httpCode: Int, errorResponse: ErrorResponse?) -> Boolean)?): Boolean {
        if (!NetworkUtils.isNetWorkAvailable(FoxRuntime.application)) {
            ToastUtil.show(FoxRuntime.application, networkErrorToast)
            return true
        }

        if (it is HttpException) {
            it.response().errorBody()
            val json = it.response().errorBody()?.string()
            if (!TextUtils.isEmpty(json)) {
                val errorResponse = JsonUtils.optFromJson<ErrorResponse>(json!!, ErrorResponse::class.java)

                val resId = errorHintMap[errorResponse?.code] ?: 0
                val hint = if (resId > 0) FoxRuntime.application.getString(resId) else null

                if (errorListener[errorResponse?.code ?: 0] != null) {
                    errorListener[errorResponse?.code ?: 0]?.onError(it.response().code(), errorResponse)
                } else {
                    if (listener == null) {
                        if (TextUtils.isEmpty(hint)) {
                            ToastUtil.show(FoxRuntime.application, errorResponse?.hint ?: errorResponse?.msg ?: "")
                        } else {
                            hint?.let {
                                ToastUtil.show(FoxRuntime.application, it)
                            }
                        }
                    } else {
                        val result: Boolean = listener.invoke(it.response().code(), errorResponse)
                        if (!result) {
                            if (TextUtils.isEmpty(hint)) {
                                ToastUtil.show(FoxRuntime.application, errorResponse?.hint ?: errorResponse?.msg ?: "")
                            } else {
                                hint?.let {
                                    ToastUtil.show(FoxRuntime.application, it)
                                }
                            }
                        }
                    }
                }
            }

        } else {
            it?.printStackTrace()
        }
        return false
    }

    interface ErrorListener {
        fun onError(httpCode: Int, errorResponse: ErrorResponse?)
    }
}