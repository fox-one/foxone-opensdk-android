package com.fox.one.cloud.operation.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-04-03
 */
data class ProtocolInfo(
    @SerializedName("privacy") val privacy: String? = "",
    @SerializedName("user") val user: String? = "",
    @SerializedName("legal_notice") val legalNotice: String? = "",
    @SerializedName("anti_money") val antiMoney: String? = "",
    @SerializedName("leveraged_trading") val leveragedTrading: String? = ""
): Serializable {
    constructor(): this("", "", "", "", "")
}