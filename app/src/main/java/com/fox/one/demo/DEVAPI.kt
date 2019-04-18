package com.fox.one.demo

import com.fox.one.ex.core.CloudAPI
import com.fox.one.ex.core.ExFavAPI
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.FoxCall
import com.fox.one.support.framework.network.HttpEngine
import okhttp3.OkHttpClient
import retrofit2.http.GET

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-06
 */
interface DEVAPI {

    @GET("/dev/device")
    fun requestDevices(): FoxCall<Any>

    object Impl: DEVAPI {

        var apiLoader = APILoader().apply {
            this.setBaseUri(APILoader.BaseUrl(ExFavAPI.ALPHA_URL, ExFavAPI.BETA_URL, ExFavAPI.RELEASE_URL))
        }

        override fun requestDevices(): FoxCall<Any> {
            return apiLoader.load(DEVAPI::class.java)
                .requestDevices()
        }

    }
}