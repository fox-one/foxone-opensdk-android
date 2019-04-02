package com.fox.one.cloud.operation

import com.fox.one.cloud.operation.model.HomeInfo
import com.fox.one.cloud.operation.model.MerchantConfigResponse
import com.fox.one.support.framework.network.FoxCall
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-28
 */
interface IOperationAPI {

    @GET("/api/merchant/bucket/{key}")
    fun getBucket(@Path("key") key: String): FoxCall<JsonElement>

    /**
     * language: zh, en, ja, ko, ...
     */
    @GET("/api/merchant/bucket/home-{language}")
    fun getHome(@Path("language") language: String): FoxCall<HomeInfo>

    @GET("/api/merchant/config")
    fun getConfig():FoxCall<MerchantConfigResponse>
}