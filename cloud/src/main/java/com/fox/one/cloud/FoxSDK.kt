package com.fox.one.cloud

import android.app.Application
import android.os.Build
import android.text.TextUtils
import android.util.Base64
import com.fox.one.passport.core.PassportAPI
import com.fox.one.pay.core.rate.CurrencyRateManager
import com.fox.one.support.common.extension.closeSilently
import com.fox.one.support.common.utils.LogUtils
import com.fox.one.support.framework.*
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.HttpEngine
import okhttp3.Interceptor
import okio.Buffer
import org.msgpack.core.MessagePack
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

            //device info
            newRequestBuilder.addHeader("x-gateway-client", getEncodedDeviceInfo())

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
            val packer = MessagePack.newDefaultBufferPacker()
            packer.packMapHeader(5)
            packer.packString("device_id")
            packer.packString(FoxRuntime.deviceId)
            packer.packString("device_name")
            packer.packString("${Build.BRAND} ${Build.DISPLAY}")
            packer.packString("device_platform")
            packer.packString("android")

            deviceInfo = Base64.encodeToString(packer.toByteArray(), Base64.NO_WRAP)
            packer.closeSilently()
        }

        return deviceInfo
    }

    private const val HEADER_MERCHANT_ID = "fox-merchant-id"
    private const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"

    data class Options(var logEnable: Boolean, var debugEnable: Boolean, var env: Enviroment, var customBaseUrl: APILoader.BaseUrl?): Serializable {

    }
}