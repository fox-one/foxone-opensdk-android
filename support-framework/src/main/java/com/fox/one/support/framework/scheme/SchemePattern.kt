package com.foxone.exchange.framework.scheme

import android.content.Context
import android.net.Uri

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-08-22
 */
class SchemePattern {

    var patterns = mutableMapOf<String, String>()
    var schemeAction: ((Context, Uri) -> Unit)  = {_, _->}

    companion object {
        const val KEY_SCHEME = "scheme"
        const val KEY_HOST = "host"
        const val KEY_PATH = "path"
    }

    fun action(action: (Context, Uri) -> Unit): SchemePattern {
        schemeAction = action

        return this
    }

    fun scheme(scheme: String): SchemePattern {
        patterns[KEY_SCHEME] = scheme

        return this
    }

    fun host(host: String): SchemePattern {
        patterns[KEY_HOST] = host

        return this
    }

    fun path(path: String): SchemePattern {
        patterns[KEY_PATH] = path

        return this
    }

    fun queryParam(key: String, value: String): SchemePattern {
        patterns[key] = value

        return this
    }

    fun getScheme(): String? {
        return patterns[KEY_SCHEME]
    }

    fun getHost(): String? {
        return patterns[KEY_HOST]
    }

    fun getPath(): String? {
        return patterns[KEY_PATH]
    }

    fun getQueryParam(key: String): String? {
        return patterns[key]
    }
}