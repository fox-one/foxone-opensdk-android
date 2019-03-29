package com.fox.one.cloud.operation

import com.fox.one.cloud.operation.model.HomeInfo
import com.fox.one.pay.core.PayAPI
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.FoxCall
import com.fox.one.support.framework.network.HttpEngine
import com.google.gson.JsonElement
import okhttp3.OkHttpClient

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-28
 */
object OperationAPI: IOperationAPI {
    override fun getHome(language: String): FoxCall<HomeInfo> {
        return apiLoader.load(IOperationAPI::class.java)
            .getHome(language)
    }

    override fun getBucket(key: String): FoxCall<JsonElement> {
        return apiLoader.load(IOperationAPI::class.java)
            .getBucket(key)
    }

    private val apiLoader = APILoader()

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpEngine.defaultInterceptor)
            .build()

        apiLoader.setOkHttp(okHttpClient)
        apiLoader.setBaseUri(APILoader.BaseUrl(PayAPI.ALPHA_URL, PayAPI.BETA_URL, PayAPI.RELEASE_URL))
    }
}