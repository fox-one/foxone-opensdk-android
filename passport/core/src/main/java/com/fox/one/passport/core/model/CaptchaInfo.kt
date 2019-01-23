package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-27
 */
data class CaptchaInfo(
    /**
     * 验证码ID
     */
    @SerializedName("captcha_id") val captchaId: String?): BasePassportResponse(), Serializable