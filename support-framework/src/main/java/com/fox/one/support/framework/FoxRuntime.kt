package com.fox.one.support.framework

import android.app.Application
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import java.lang.StringBuilder

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-17
 */

object FoxRuntime {

    private const val PREF_KEY_ENV = "pref_key_env"
    private const val PREF_KEY_DEBUG = "pref_key_debug"

    lateinit var application: Application

    val handler: Handler = Handler(Looper.getMainLooper())

    val versionCode: Int by lazy { application.packageManager.getPackageInfo(application.packageName, 0).versionCode }

    val versionName: String by lazy { application.packageManager.getPackageInfo(application.packageName, 0).versionName }

    val deviceId: String by lazy { Settings.System.getString(application.contentResolver, Settings.Secure.ANDROID_ID) }

    var userAgent: String = System.getProperty("http.agent")

    var env: Enviroment = Enviroment.RELEASE
        set(value) {
            field = value
            FoxPreference.instance(application).putInt(PREF_KEY_ENV, value.value)
        }
    var debug: Boolean = false
        set(value) {
            field = value
            FoxPreference.instance(application).putBoolean(PREF_KEY_DEBUG, value)
        }

    fun init(application: Application) {
        this.application = application

        APPLifeCycleManager.init(application)

        env = Enviroment.valueOf(FoxPreference.instance(application).getInt(PREF_KEY_ENV, Enviroment.RELEASE.value))
        debug = FoxPreference.instance(application).getBoolean(PREF_KEY_DEBUG, false)

        val deviceName = "${Build.BRAND}_${Build.DISPLAY}".replace(" ", "_")
        userAgent = StringBuilder(System.getProperty("http.agent"))
            .append(" DeviceId/$deviceId")
            .append(" DeviceName/$deviceName")
            .append(" DevicePlatform/android")
            .toString()
    }
}