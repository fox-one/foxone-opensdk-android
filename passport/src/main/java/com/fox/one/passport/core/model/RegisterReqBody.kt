package com.fox.one.passport.core.model

import com.fox.one.support.common.utils.toMD5
import com.google.gson.annotations.SerializedName

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-28
 */
data class RegisterReqBody(
    /**
     * nick name
     */
    @SerializedName("name") val nickName: String,
    /**
     * 短信验证码
     */
    @SerializedName("code") val validCode: String,
    /**
     * 短信验证码接口返回的token
     */
    @SerializedName("token") val token: String,
    /**
     * 密码
     */
    @SerializedName("password") var password: String) {
    init {
        password = "fox.$password".toMD5()
    }
}