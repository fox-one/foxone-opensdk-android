package com.fox.one.support.passport.core.models

import com.fox.one.support.common.utils.toMD5
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-24
 */
data class ModifyPasswordReqBody(
    /**
     * request token
     */
    @SerializedName("token") var token: String,
    /**
     * 原始密码
     */
    @SerializedName("password") var originPassword: String,
    /**
     * 新密码
     */
    @SerializedName("new_password") var newPassword: String): Serializable {
    init {
        originPassword = "fox.$originPassword".toMD5()
        newPassword = "fox.$newPassword".toMD5()
    }
}