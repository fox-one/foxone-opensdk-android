package com.fox.one.passport.core.model

import com.fox.one.support.framework.network.BaseResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-02-26
 */
class TFARequiredLoginResponse: BaseResponse() {

    @SerializedName("code")
    var code: Int = 0

    @SerializedName("msg")
    var msg: String = ""

    @SerializedName("data")
    var data: Data = Data("")

    override fun code(): Int {
        return code
    }

    override fun isSuccessful(): Boolean {
        return code == 0
    }

    data class Data(@SerializedName("tfa_token") val tfaToken: String? = ""): Serializable {

    }
}