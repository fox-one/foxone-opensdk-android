package com.fox.one.support.passport.core.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-28
 */
data class EmailCodeReqBody(
    /**
     * 验证码
     */
    @SerializedName("captcha") val captcha: String,
    /**
     * 验证码ID
     */
    @SerializedName("captcha_id") val captchaId: String,
    /**
     * 邮箱地址
     */
    @SerializedName("email") val email: String): Serializable