package com.fox.one.ex.core.stream

import com.fox.one.ex.core.ExchangeAPI
import com.fox.one.ex.core.model.ExBaseResponse
import com.fox.one.ex.core.stream.model.ListenKeyResponse
import com.fox.one.ex.core.stream.model.ResetListenKeyReqBody
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.FoxCall
import com.fox.one.support.framework.network.HttpEngine
import okhttp3.OkHttpClient

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-24
 */
object StreamDataAPI: IStreamDataAPI {
    override fun getListenKey(): FoxCall<ListenKeyResponse> {
        return apiloader.load(IStreamDataAPI::class.java)
            .getListenKey()
    }

    override fun resetListenKey(request: ResetListenKeyReqBody): FoxCall<ExBaseResponse> {
        return apiloader.load(IStreamDataAPI::class.java)
            .resetListenKey(request)
    }

    override fun deleteListenKey(key: String): FoxCall<ExBaseResponse> {
        return apiloader.load(IStreamDataAPI::class.java)
            .deleteListenKey(key)
    }

    private val apiloader = APILoader()

    var apiLoader = APILoader()

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpEngine.defaultInterceptor)
            .build()

        apiLoader.setOkHttp(okHttpClient)
        apiLoader.setBaseUri(APILoader.BaseUrl(ExchangeAPI.ALPHA_URL, ExchangeAPI.BETA_URL, ExchangeAPI.RELEASE_URL))
    }


}