package com.fox.one.passport.core.model

import com.fox.one.support.common.utils.toMD5
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-28
 */
data class LoginWithPhoneReqBody(

    /**
     * 国家编码, e.g., 86
     */
    @SerializedName("phone_code") val countryCode: String,
    /**
     * 手机号
     */
    @SerializedName("phone_number") val phoneNumber: String,
    /**
     * 密码
     */
    @SerializedName("password") var password: String): Serializable {
    init {
        password = "fox.$password".toMD5()
    }
}