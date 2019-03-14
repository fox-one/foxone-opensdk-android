package com.fox.one.support.passport.core

import com.fox.one.passport.core.model.*
import com.fox.one.support.framework.network.FoxCall
import retrofit2.http.*

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-21
 */
interface IPassportAPI {

    /**
     * 获取图片验证码信息
     */
    @POST("/api/captcha")
    fun requestCaptcha(): FoxCall<CaptchaInfo>

    /**
     * 获取手机注册用的短信验证码
     */
    @POST("/api/account/request_register")
    fun requestValidCodeOfRegister(@Body request: SMSCodeReqBody): FoxCall<ValidCodeResponse>

    /**
     * 获取邮箱注册用的邮箱验证码
     */
    @POST("/api/account/request_register")
    fun requestValidCodeOfRegister(@Body request: EmailCodeReqBody): FoxCall<ValidCodeResponse>

    /**
     * 注册，包括手机注册&邮箱注册
     */
    @POST("/api/account/register")
    fun register(@Body request: RegisterReqBody): FoxCall<AccountInfo>

    /**
     * 通过短信验证码快速登录
     */
    @POST("/api/account/login_phone")
    fun login(@Body request: LoginWithSMSReqBody): FoxCall<AccountInfo>

    /**
     * 获取短信登录的短信验证码
     */
    @POST("/api/account/request_login_phone")
    fun requestCodeOfSMSLogin(@Body request: SMSCodeReqBody): FoxCall<ValidCodeResponse>

    /**
     * 用手机号密码登录
     */
    @POST("/api/account/login")
    fun login(@Body request: LoginWithPhoneReqBody): FoxCall<AccountInfo>

    @POST("/api/account/login")
    fun login(@Body request: LoginWithEmailReqBody): FoxCall<AccountInfo>

    /**
     * 获取账号信息
     */
    @GET("/api/account/detail")
    fun getAccountInfo(): FoxCall<AccountInfo>

    @PUT("/api/account/profile")
    fun updateUserProfile(@Body request: UpdateUserProfileReqBody): FoxCall<BasePassportResponse>

    /**
     * 登出
     */
    @POST("/api/account/logout")
    fun logout(): FoxCall<BasePassportResponse>

    /**
     * 修改密码
     */
    @POST("/api/account/modify_password")
    fun modifyPassword(@Body request: ModifyPasswordReqBody): FoxCall<BasePassportResponse>

    /**
     * 获取重置密码的短信验证码
     */
    @POST("/api/account/request_reset_password")
    fun requestValidCodeOfResetPassword(@Body request: SMSCodeReqBody): FoxCall<ValidCodeResponse>

    /**
     * 获取重置邮箱密码的邮箱验证码
     */
    @POST("/api/account/request_reset_password")
    fun requestValidCodeOfResetPassword(@Body request: EmailCodeReqBody): FoxCall<ValidCodeResponse>

    /**
     * 重置登录密码，包括手机账号密码&邮箱账号密码
     */
    @POST("/api/account/reset_password")
    fun resetPassword(@Body request: ResetPasswordReqBody): FoxCall<BasePassportResponse>

    @POST("/api/account/tfa/request")
    fun requestTFA():FoxCall<TFAResponse>

    @POST("/api/account/tfa/on")
    fun enableTFA(@Body request: TFAReqBody): FoxCall<BasePassportResponse>

    @POST("/api/account/tfa/off")
    fun disableTFA(@Body request: TFAReqBody): FoxCall<BasePassportResponse>

    @POST("/api/account/login_tfa")
    fun login(@Body request: TFALoginReqBody): FoxCall<AccountInfo>

    @GET("/api/account/sessions")
    fun requestLoginSessions(): FoxCall<LoginSessionResponse>

    @DELETE("/api/account/session/{sessionKey}")
    fun removeLoginSession(@Path("sessionKey") sessionKey: String): FoxCall<BasePassportResponse>

    @POST("/api/account/api_key/create")
    fun createAPIKey(@Body request: CreateAPIKeyReqBody): FoxCall<CreateAPIKeyResponse>

    @GET("/api/account/api_key/list")
    fun requestAPIKeys(): FoxCall<List<APIKeyInfo>>

    @DELETE("/api/account/api_key/{key}")
    fun removeAPIKey(@Path("key") key: String): FoxCall<BasePassportResponse>

    @POST("/api/account/user_auth/request")
    fun requestUserAuthInfo(): FoxCall<UserAuthInfo>

    @POST("/api/account/user_auth/sms/request")
    fun requestAuthSMSCode(@Body request: AuthReqBody): FoxCall<BasePassportResponse>

    @POST("/api/account/user_auth/email/request")
    fun requestAuthEmailCode(@Body request: AuthReqBody): FoxCall<BasePassportResponse>

    @POST("/api/account/user_auth/verify")
    fun verifyAuth(@Body request: VerifyAuthReqBody): FoxCall<BasePassportResponse>

    @GET("/api/account/withdraw/status")
    fun requestWithdrawStatus(): FoxCall<WithdrawStatusResponse>

    @POST("/api/account/withdraw/request")
    fun requestWithdraw(@Body request: RequestWithdrawReqBody): FoxCall<RequestWithdrawResponse>

    @POST("/api/account/auth/{requestId}")
    fun authWithdraw(@Path("requestId") requestId: Long, @Body request: AuthReqBody): FoxCall<RequestWithdrawResponse>

    @GET("/api/account/withdraw/fee")
    fun requestWithdrawFee(@Body request: WithdrawFeeReqBody): FoxCall<WithdrawFeeResponse>
}