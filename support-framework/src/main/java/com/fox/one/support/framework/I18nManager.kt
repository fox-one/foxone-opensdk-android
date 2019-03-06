package com.fox.one.support.framework

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.text.TextUtils
import java.util.*

/**
 * Created by zhangyinghao on 2018/5/31.
 */
object I18nManager {

    private const val PREF_KEY_I18N_LANGUAGE = "i18n_language"
    private const val PREF_KEY_I18N_COUNTRY = "i18n_country"

    var locale: Locale = Locale.getDefault()
        set(value) {
            field = value
            FoxPreference.instance(FoxRuntime.application).putString(PREF_KEY_I18N_COUNTRY, locale.country)
            FoxPreference.instance(FoxRuntime.application).putString(PREF_KEY_I18N_LANGUAGE, locale.language)
        }

    fun init(context: Context) {
        val language = FoxPreference.instance(context).getString(PREF_KEY_I18N_LANGUAGE, "")
        val country = FoxPreference.instance(context).getString(PREF_KEY_I18N_COUNTRY, "")

        locale = if (TextUtils.isEmpty(language) || TextUtils.isEmpty(country)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.resources.configuration.locales[0]
            } else {
                context.resources.configuration.locale
            }
        } else {
            Locale(language, country)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context.resources.configuration.setLocale(locale)
        } else {
            context.resources.configuration.locale = locale
        }

        context.resources.updateConfiguration(context.resources.configuration, context.resources.displayMetrics)
    }

    fun refreshAppConfiguration(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context.resources.configuration.setLocale(locale)
        } else {
            context.resources.configuration.locale = locale
        }
        context.resources.updateConfiguration(context.resources.configuration, context.resources.displayMetrics)
    }

    fun attachBaseContext(context: Context?): Context? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateResources(context)
            } else {
                context
            }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context?): Context? {
        val configuration = context?.resources?.configuration
        configuration?.setLocale(locale)
        configuration?.locales = LocaleList(locale)
        return context?.createConfigurationContext(configuration)
    }
}

fun Locale.formatWithUnderLine(): String {
    return formatWith("_")
}

fun Locale.formatWithMiddleLine(): String {
    return formatWith("-")
}

fun Locale.formatWith(combine: String): String {
    return if (TextUtils.isEmpty(country)) language else "$language$combine$country"
}
