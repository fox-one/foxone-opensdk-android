package com.fox.one.ex.core

import com.fox.one.ex.core.model.ExBaseResponse
import com.fox.one.passport.core.PassportAPI
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.FoxCall
import com.fox.one.support.framework.network.HttpEngine
import okhttp3.OkHttpClient

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-23
 */
object ExExFavAPI: IExFavAPI {

    var apiLoader = APILoader()

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpEngine.defaultInterceptor)
            .build()

        apiLoader.setOkHttp(okHttpClient)
        apiLoader.setBaseUri(APILoader.BaseUrl(PassportAPI.ALPHA_URL, PassportAPI.BETA_URL, PassportAPI.RELEASE_URL))
    }

    override fun getFavPairs(): FoxCall<MutableList<String>> {
        return apiLoader.load(IExFavAPI::class.java)
            .getFavPairs()
    }

    override fun like(request: List<String>): FoxCall<ExBaseResponse> {
        return apiLoader.load(IExFavAPI::class.java)
            .like(request)
    }

    override fun dislike(request: List<String>): FoxCall<ExBaseResponse> {
        return apiLoader.load(IExFavAPI::class.java)
            .dislike(request)
    }
}