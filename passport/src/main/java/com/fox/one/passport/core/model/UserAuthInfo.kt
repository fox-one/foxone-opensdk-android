package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-14
 */
data class UserAuthInfo(@SerializedName("email") val email: String?,
                        @SerializedName("sms") val sms: String?,
                        @SerializedName("auth_token") val authToken: String?,
                        @SerializedName("verify_email") val needVerifyEmail: Boolean,
                        @SerializedName("verify_sms") val needVerifySMS: Boolean,
                        @SerializedName("verify_tfa") val needVerifyTFA: Boolean): Serializable {
    constructor(): this("", "", "", false, false, false)
}