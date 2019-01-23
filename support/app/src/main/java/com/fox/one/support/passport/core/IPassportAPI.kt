package com.fox.one.support.passport.core

import com.fox.one.support.framework.network.FoxCall
import com.fox.one.support.passport.core.models.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

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
    @POST("/api/account/request_register_phone")
    fun requestSMSCodeOfPhoneRegister(@Body request: SMSCodeReqBody): FoxCall<SMSCodeResponse>

    /**
     * 手机注册
     */
    @POST("/api/account/register_phone")
    fun registerWithPhone(@Body request: PhoneRegisterReqBody): FoxCall<AccountInfo>

//        /**
//         * 邮箱注册
//         */
//        @POST("api/account/register_email")
//        fun registerWithEmail(@Body request: EmailRegisterReqBody): FoxCall<String>
//
//        /**
//         * 激活邮箱
//         */
//        @POST("api/account/verify_email")
//        fun activeEmail(@Body request: ActiveEmailReqBody): FoxCall<BaseResponse>
//
//        /**
//         * 重新获取邮箱验证码
//         */
//        @POST("api/account/resend_email_verify_code")
//        fun resendEmailCode(@Body request: ResendEmailCodeReqBody): FoxCall<BaseResponse>
//
//        /**
//         * 用邮箱密码登录
//         */
//        @POST("api/account/login")
//        fun loginWithEmail(@Body request: LoginWithEmailReqBody): FoxCall<UserInfo>

    /**
     * 用手机号密码登录
     */
    @POST("/api/account/login")
    fun loginWithPhone(@Body request: LoginWithPhoneReqBody): FoxCall<AccountInfo>

    /**
     * 通过短信验证码快速登录
     */
    @POST("/api/account/login_phone")
    fun loginWithSMSCode(@Body request: LoginWithSMSReqBody): FoxCall<AccountInfo>

    /**
     * 获取短信登录的短信验证码
     */
    @POST("/api/account/request_login_phone")
    fun requestCodeOfSMSLogin(@Body request: SMSCodeReqBody): FoxCall<SMSCodeResponse>

    /**
     * 获取账号信息
     */
    @GET("/api/account/detail")
    fun getAccountInfo(): FoxCall<AccountInfo>

    /**
     * 登出
     */
    @DELETE("/api/account/logout")
    fun logout(): FoxCall<BasePassportResponse>

    /**
     * 申请修改密码
     */
    @POST("/api/account/request_modify_password")
    fun requestModifyPassword(@Body request: SMSCodeReqBody): FoxCall<BasePassportResponse>

    /**
     * 修改密码
     */
    @POST("/api/account/password")
    fun modifyPassword(@Body request: ModifyPasswordReqBody): FoxCall<BasePassportResponse>

    /**
     * 获取重置密码的短信验证码
     */
    @POST("/api/account/request_reset_password")
    fun requestSMSCodeOfResetPhonePassword(@Body request: SMSCodeReqBody): FoxCall<SMSCodeReqBody>

    /**
     * 重置手机登录密码
     */
    @POST("/api/account/reset_password")
    fun resetPhonePassword(@Body request: ResetPhonePasswordReqBody): FoxCall<BasePassportResponse>

//        /**
//         * 获取重置邮箱密码的邮箱验证码
//         */
//        @POST("api/account/request_reset_password_email")
//        fun requestCodeOfResetEmailPassword(@Body request: EmailCodeReqBody): FoxCall<BaseResponse>
//
//        /**
//         * 重置邮箱密码
//         */
//        @POST("api/account/reset_password")
//        fun resetEmailPassword(@Body request: ResetEmailPasswordReqBody): FoxCall<BaseResponse>

//        //TODO
//        @POST("api/account/request_enable_tfa")
//        fun requestTFAAbility(): FoxCall<BaseResponse>
//
//        //TODO
//        @POST("api/account/enable_tfa")
//        fun enableTFA():FoxCall<BaseResponse>
//
//        //TODO
//        @POST("api/account/turn_off_tfa")
//        fun closeTFA():FoxCall<BaseResponse>
//
//        //TODO
//        @POST("api/account/verify_tfa")
//        fun verifyTFA():FoxCall<BaseResponse>

}