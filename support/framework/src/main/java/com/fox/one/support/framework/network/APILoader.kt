package com.fox.one.support.framework.network

import android.os.Handler
import android.os.Looper
import com.fox.one.support.common.utils.JsonUtils
import com.fox.one.support.framework.Enviroment
import com.fox.one.support.framework.FoxRuntime
import com.fox.one.support.framework.network.FoxCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable
import java.util.concurrent.Executor

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-07-14
 */
class APILoader {

    private var baseUrl: BaseUrl = BaseUrl("", "", "")
    private var httpEngine = HttpEngine()
    /**
     * use default base url with HttpEngine.getBaseUrl(); use default HttpEngine
     */
    fun <T> load(clazz: Class<T>): T {
        val retrofit = Retrofit.Builder().baseUrl(getBaseUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(FoxCallAdapterFactory(MainThreadExecutor()))
                .addConverterFactory(GsonConverterFactory.create(JsonUtils.getGson()))
                .client(getHttpClient())
                .build()

        return retrofit.create(clazz) as T
    }

    /**
     * use default HttpEngine, and specified baseUrl
     */
    fun <T> load(baseUrl: String, clazz: Class<T>): T {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(FoxCallAdapterFactory(MainThreadExecutor()))
                .addConverterFactory(GsonConverterFactory.create(JsonUtils.getGson()))
                .client(getHttpClient())
                .build()


        return retrofit.create(clazz) as T
    }

    /**
     * use default baseUrl and specified HttpEngine
     */
    fun <T> load(engine: HttpEngine, clazz: Class<T>): T {
        val retrofit = Retrofit.Builder().baseUrl(getBaseUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(FoxCallAdapterFactory(MainThreadExecutor()))
                .addConverterFactory(GsonConverterFactory.create(JsonUtils.getGson()))
                .client(engine.okHttpClient)
                .build()

        return retrofit.create(clazz) as T
    }

    fun <T> load(engine: HttpEngine, baseUrl: String, clazz: Class<T>): T {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(FoxCallAdapterFactory(MainThreadExecutor()))
                .addConverterFactory(GsonConverterFactory.create(JsonUtils.getGson()))
                .client(engine.okHttpClient)
                .build()

        return retrofit.create(clazz) as T
    }

    fun setBaseUri(baseUrl: BaseUrl) {
        this.baseUrl = baseUrl
    }

    fun getBaseUrl(): String {
        return when (FoxRuntime.env) {
            Enviroment.RELEASE -> baseUrl.releaseUrl
            Enviroment.BETA -> baseUrl.betaUrl
            Enviroment.ALPHA -> baseUrl.alphaUrl
        }
    }

    private fun getHttpClient(): OkHttpClient {
        httpEngine.debugable(FoxRuntime.debug)
        return httpEngine.okHttpClient
    }

    fun setOkHttp(okHttpClient: OkHttpClient) {
        httpEngine.okHttpClient = okHttpClient
    }

    companion object {
        var defaultBaseUrl: BaseUrl = BaseUrl()
    }

    internal class MainThreadExecutor : Executor {
        private val handler = Handler(Looper.getMainLooper())

        override fun execute(r: Runnable) {
            handler.post(r)
        }
    }

    data class BaseUrl(val alphaUrl: String, val betaUrl: String, val releaseUrl: String): Serializable {
        constructor():this("", "", "")
    }
}