package com.fox.one.support.framework.scheme

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.text.TextUtils
import com.fox.one.support.framework.FoxRuntime

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-08-21
 */
object SchemeRoute {
    const val KEY_SCHEME_URI = "uri"

    var activeScheme: Uri? = null

    var schemePatterns = mutableListOf<SchemePattern>()

    fun registerScheme(schemePattern: SchemePattern) {
        schemePatterns.add(schemePattern)
    }

    fun handleScheme(context: Context, uri: Uri?) {
        uri?.also {uriInner ->
            val matchedPatterns = schemePatterns.filter {
                var matched = true

                it.patterns.forEach { entry ->
                    matched = when(entry.key) {
                        SchemePattern.KEY_SCHEME -> {
                            if (!TextUtils.isEmpty(entry.value)) {
                                entry.value == uriInner.scheme
                            } else {
                                true
                            }
                        }
                        SchemePattern.KEY_HOST -> {
                            if (!TextUtils.isEmpty(entry.value)) {
                                entry.value == uriInner.host
                            } else {
                                true
                            }
                        }
                        SchemePattern.KEY_PATH -> {
                            if (!TextUtils.isEmpty(entry.value)) {
                                entry.value == uriInner.path
                            } else {
                                true
                            }
                        }
                        else -> {
                            if (!TextUtils.isEmpty(entry.value)) {
                                entry.value == uriInner.getQueryParameter(entry.key)
                            } else {
                                true
                            }
                        }
                    }
                    //

                    if (!matched) {
                        return@forEach
                    }
                }

                if (it.patterns[SchemePattern.KEY_SCHEME] == null
                        && it.patterns[SchemePattern.KEY_HOST] == null
                        && it.patterns[SchemePattern.KEY_PATH] == null) {
                    matched = false
                }

                return@filter matched
            }

            val rightPattern = matchedPatterns.maxBy {  return@maxBy it.patterns.size}

            rightPattern?.schemeAction?.invoke(context, uriInner)
        }
    }

    fun dispatch(context: Context, uri: Uri?, fromPush: Boolean) {
        SchemeActivity.start(context, fromPush, uri)
    }

    fun dispatch(context: Context, uri: Uri?) {
        SchemeActivity.start(context, false, uri)
    }

    fun wakeupAPP() {
        try {
            val intent = FoxRuntime.application.packageManager.getLaunchIntentForPackage(FoxRuntime.application.packageName)
            if (intent != null) {
                intent.action = Intent.ACTION_MAIN
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addCategory(Intent.CATEGORY_LAUNCHER)

                FoxRuntime.application.startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun gotoSetting(context: Context) {
        try {
            val localIntent = Intent()
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            localIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            localIntent.data = Uri.fromParts("package", context.packageName, null)

            context.startActivity(localIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun gotoNotificationSetting(context: Context) {
        try {
            val intent = Intent()
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            //for api 26
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, context.applicationInfo.uid)

            //for api 21~25
            intent.putExtra("app_package", context.packageName)
            intent.putExtra("app_uid", context.applicationInfo.uid)

            context.startActivity(intent)
        } catch (e: Exception) {
            gotoSetting(context)
        }
    }
}