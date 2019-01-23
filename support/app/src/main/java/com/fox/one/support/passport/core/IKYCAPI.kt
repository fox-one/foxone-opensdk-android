package com.fox.one.support.passport.core

import com.fox.one.support.framework.network.FoxCall
import com.foxone.exchange.account.core.model.KYCProfileInfo
import com.foxone.exchange.account.core.model.KYCProfileReqBody
import com.foxone.exchange.account.core.model.KYCProfileResponse
import com.foxone.exchange.account.core.model.KYCStatusResponse
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-22
 */
interface IKYCAPI {

    /**
     * 创建并上传身份认证信息
     */
    @POST("/api/account/kyc/profile")
    fun createKYCProfile(@Body request: KYCProfileReqBody): FoxCall<KYCStatusResponse>

    /**
     * 更新身份认证信息
     */
    @PUT("api/kyc/update")
    fun updateKYCProfile(@Body request: KYCProfileReqBody): FoxCall<KYCStatusResponse>

    /**
     * 获取身份认证信息
     */
    @GET("api/kyc/profile")
    fun getKYCProfile(): FoxCall<KYCProfileInfo>
}