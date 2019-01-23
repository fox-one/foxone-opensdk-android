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
data class EmailRegisterReqBody(
    /**
     * 邮箱地址
     */
    @SerializedName("email") val email: String,
    /**
     * 密码
     */
    @SerializedName("password") var password: String): Serializable {
    init {
        password = "fox.$password".toMD5()
    }
}