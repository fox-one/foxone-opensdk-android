package com.fox.one.ex.core.stream

import com.fox.one.ex.core.model.ExBaseResponse
import com.fox.one.ex.core.stream.model.ListenKeyResponse
import com.fox.one.ex.core.stream.model.ResetListenKeyReqBody
import com.fox.one.support.framework.network.FoxCall
import retrofit2.http.*

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-17
 */
interface IStreamDataAPI {
    /**
     * 获取监听的Key
     */
    @POST("/member/exchange/listen_key")
    fun getListenKey(): FoxCall<ListenKeyResponse>

    /**
     * 重置监听的Key
     */
    @PUT("/member/exchange/listen_key")
    fun resetListenKey(@Body request: ResetListenKeyReqBody): FoxCall<ExBaseResponse>

    /**
     * 删除监听的Key
     */
    @DELETE("/member/exchange/listen_key/{key}")
    fun deleteListenKey(@Path("key") key: String): FoxCall<ExBaseResponse>
}