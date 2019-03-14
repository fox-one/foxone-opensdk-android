package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-14
 */
data class VerifyAuthReqBody(@SerializedName("auth_token") val authToken: String,
                             @SerializedName("sms_code") val smsCode: String,
                             @SerializedName("email_code") val emailCode: String,
                             @SerializedName("tfa_code") val tfaCode: String,
                             @SerializedName("purpose") val purpose: String): Serializable {

    companion object {
        const val PURPOSE_WITHDRAW = "withdraw"
        const val PURPOSE_CREATE_API = "create_api_session"
    }
}