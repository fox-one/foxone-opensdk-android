package com.fox.one.cloud

import android.app.Application
import com.fox.one.passport.core.PassportAPI
import com.fox.one.pay.core.rate.CurrencyRateManager
import com.fox.one.support.common.extension.closeSilently
import com.fox.one.support.common.utils.LogUtils
import com.fox.one.support.framework.APPLifeCycleManager
import com.fox.one.support.framework.AppLifecycleCallback
import com.fox.one.support.framework.Enviroment
import com.fox.one.support.framework.FoxRuntime
import com.fox.one.support.framework.network.HttpEngine
import com.fox.one.support.framework.network.HttpErrorHandler
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

    fun init(application: Application, merchantId: String, options: Options?) {
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

            LogUtils.i("foxone", "${request.url().toString()}")
            val newRequestBuilder = it.request().newBuilder()
            newRequestBuilder.addHeader(HEADER_MERCHANT_ID, merchantId)

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

    private const val HEADER_MERCHANT_ID = "fox-cloud-merchant-id"

    data class Options(var logEnable: Boolean, var debugEnable: Boolean, var env: Enviroment): Serializable {

    }
}