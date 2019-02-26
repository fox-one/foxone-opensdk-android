package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-02-26
 */
data class TFALoginReqBody(@SerializedName("tfa_token") val tfaToken: String,
                           @SerializedName("code") val code: String): Serializable {
}