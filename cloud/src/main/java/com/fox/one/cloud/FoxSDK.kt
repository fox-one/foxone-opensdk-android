package com.fox.one.cloud

import android.app.Application
import android.os.Build
import android.text.TextUtils
import android.util.Base64
import com.fox.one.passport.core.PassportAPI
import com.fox.one.pay.core.rate.CurrencyRateManager
import com.fox.one.support.common.extension.closeSilently
import com.fox.one.support.common.utils.JsonUtils
import com.fox.one.support.common.utils.LogUtils
import com.fox.one.support.framework.*
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.HttpEngine
import com.google.gson.annotations.SerializedName
import okhttp3.Interceptor
import okio.Buffer
import java.io.Serializable
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-23
 */
object FoxSDK {

    var merchantId: String = ""
    var deviceInfo: String? = null

    fun init(application: Application, merchantId: String, options: Options?) {

        this.merchantId = merchantId
        FoxRuntime.init(application)

        // config options
        options?.let {
            FoxRuntime.debug = it.debugEnable
            FoxRuntime.env = it.env
            LogUtils.enable(it.logEnable)
        }

        //init http engine default interceptor
        HttpEngine.defaultInterceptor = Interceptor {
            val request = it.request()

            LogUtils.i("foxone", "merchantId:${this.merchantId}, url:${request.url().toString()}")
            val newRequestBuilder = it.request().newBuilder()
            newRequestBuilder.addHeader(HEADER_MERCHANT_ID, this.merchantId)
            newRequestBuilder.addHeader(HEADER_ACCEPT_LANGUAGE, I18nManager.locale.formatWithMiddleLine())
            newRequestBuilder.addHeader(HEADER_DEVICE_INFO, getEncodedDeviceInfo())

            //sign when login
            if (PassportAPI.isLogin()) {
                //sign
                val timeInSecond = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
                val nonce = UUID.randomUUID().toString()

                val sink = Buffer()
                request.body()?.writeTo(sink)
                val bodyString = sink.readString(Charset.defaultCharset())
                sink.closeSilently()

                val signResult = PassportAPI.sign(request.method(), request.url().url().toString(), timeInSecond, nonce, bodyString)

                newRequestBuilder.header(HttpEngine.HEADER_KEY_AUTHORIZATION, HttpEngine.HEADER_VALUE_AUTHORIZATION_PREFIX + signResult.sign)
                newRequestBuilder.url(signResult.newUrl)
            }

            return@Interceptor it.proceed(newRequestBuilder.build())
        }

        APILoader.customBaseUrl = options?.customBaseUrl

        //init app life cycle
        APPLifeCycleManager.registAppLifecycleCallback(object: AppLifecycleCallback {
            override fun onAppGoToBackGround() {
                CurrencyRateManager.stopSync()
            }

            override fun onAppGoToForeGround() {
                CurrencyRateManager.startSync()
            }
        })

        //init Currency rate manager
        CurrencyRateManager.init(application)
    }

    private fun getEncodedDeviceInfo(): String? {
        if (TextUtils.isEmpty(deviceInfo)) {
            val di = DeviceInfo().toString()
            LogUtils.i("foxone", di)
            deviceInfo = Base64.encodeToString(di.toByteArray(), Base64.NO_WRAP)
        }

        LogUtils.i("foxone", "device_info:$deviceInfo")
        return deviceInfo
    }

    private const val HEADER_MERCHANT_ID = "fox-merchant-id"
    private const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"
    private const val HEADER_USER_AGENT = "User-Agent"
    private const val HEADER_DEVICE_INFO = "device_info"

    data class Options(var logEnable: Boolean, var debugEnable: Boolean, var env: Enviroment, var customBaseUrl: APILoader.BaseUrl?): Serializable {

    }

    data class DeviceInfo(
        @SerializedName("device_id") val deviceId: String,
        @SerializedName("device_name") val deviceName: String,
        @SerializedName("device_platform") val devicePlatform: String
    ): Serializable {
        constructor(): this(FoxRuntime.deviceId, "${Build.BRAND} ${Build.DISPLAY}", "android")

        override fun toString(): String {
            return JsonUtils.optToJson(this)
        }
    }
}