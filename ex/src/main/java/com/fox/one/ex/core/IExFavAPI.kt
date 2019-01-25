package com.fox.one.ex.core

import com.fox.one.ex.core.model.ExBaseResponse
import com.fox.one.support.framework.network.FoxCall
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-23
 */
interface IExFavAPI {
    /**
     * 获取用户收藏的交易币对信息
     */
    @GET("/api/account/market/pairs")
    fun getFavPairs(): FoxCall<MutableList<String>>

    /**
     * 收藏
     */
    @POST("api/account/market/pairs/like")
    fun like(@Body request: List<String>): FoxCall<ExBaseResponse>

    /**
     * 取消收藏
     */
    @POST("/api/account/market/pairs/dislike")
    fun dislike(@Body request: List<String>): FoxCall<ExBaseResponse>
}