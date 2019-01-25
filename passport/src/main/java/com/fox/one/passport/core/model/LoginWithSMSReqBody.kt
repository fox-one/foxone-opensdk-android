package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-08
 */
data class LoginWithSMSReqBody(
    /**
     * 手机号
     */
    @SerializedName("token") var token: String,
    /**
     * 短信验证码
     */
    @SerializedName("code") var smsCode: String): Serializable {
}