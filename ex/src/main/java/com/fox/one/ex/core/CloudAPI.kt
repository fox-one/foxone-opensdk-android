package com.fox.one.ex.core

import com.fox.one.ex.core.model.CoinPairInfo
import com.fox.one.pay.core.model.AssetInfo
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.FoxCall
import com.fox.one.support.framework.network.HttpEngine
import okhttp3.OkHttpClient
import retrofit2.http.GET

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-26
 */
interface CloudAPI {

    /**
     * 获取用户的所有资产（资产列表）
     */
    @GET("/api/merchant/market/assets")
    fun getAssets(): FoxCall<List<AssetInfo>>

    /**
     * 获取交易所所有币对信息
     */
    @GET("/api/merchant/market/pairs")
    fun getPairs(): FoxCall<List<CoinPairInfo>>

    object Impl: CloudAPI {
        override fun getAssets(): FoxCall<List<AssetInfo>> {
            return apiLoader.load(CloudAPI::class.java)
                .getAssets()
        }

        override fun getPairs(): FoxCall<List<CoinPairInfo>> {
            return apiLoader.load(CloudAPI::class.java)
                .getPairs()
        }

        var apiLoader = APILoader()

        init {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpEngine.defaultInterceptor)
                .build()

            apiLoader.setOkHttp(okHttpClient)
            apiLoader.setBaseUri(APILoader.BaseUrl(ExFavAPI.ALPHA_URL, ExFavAPI.BETA_URL, ExFavAPI.RELEASE_URL))
        }
    }
}