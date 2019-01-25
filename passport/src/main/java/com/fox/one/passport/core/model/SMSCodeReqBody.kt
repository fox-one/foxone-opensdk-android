package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-27
 */
data class SMSCodeReqBody(
    /**
     * 图片验证码
     */
    @SerializedName("captcha") val captcha: String,
    /**
     * 图片验证码ID
     */
    @SerializedName("captcha_id") val captchaId: String,
    /**
     * 国家编码，e.g.,86
     */
    @SerializedName("phone_code") val countryCode: String,
    /**
     * 手机号
     */
    @SerializedName("phone_number") val phoneNumber: String)