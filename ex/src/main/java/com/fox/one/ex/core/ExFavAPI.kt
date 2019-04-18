package com.fox.one.ex.core

import com.fox.one.ex.core.model.ExBaseResponse
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
object ExFavAPI: IExFavAPI {

    const val ALPHA_URL = "https://dev-gateway.fox.one"
    const val BETA_URL = "https://openapi.fox.one"
    const val RELEASE_URL = "https://openapi.fox.one"

    var apiLoader = APILoader().apply {
        this.setBaseUri(APILoader.BaseUrl(ALPHA_URL, BETA_URL, RELEASE_URL))
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