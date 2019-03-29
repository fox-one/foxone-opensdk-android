package com.fox.one.cloud.operation.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-29
 */
data class HomeInfo(@SerializedName("banners") val banners: List<BannerInfo>? = emptyList(),
                    @SerializedName("ads") val ads: List<BannerInfo>? = emptyList(),
                    @SerializedName("announces") val announces: List<BannerInfo>? = emptyList(),
                    @SerializedName("protocols") val protocols: ProtocolInfo? = ProtocolInfo()): Serializable {

    data class BannerInfo(
        @SerializedName("id") val id: String? = "",
        @SerializedName("title") val title: String? = "",
        @SerializedName("description") val description: String? = "",
        @SerializedName("image") val image: String? = "",
        @SerializedName("image_web") val imageWeb: String? = "",
        @SerializedName("url") val actionUrl: String? = ""
    ): Serializable {
        constructor(): this("", "", "", "", "", "")
    }

    data class ProtocolInfo(
        @SerializedName("privacy") val privacy: String? = "",
        @SerializedName("user") val user: String? = "",
        @SerializedName("legal_notice") val legalNotice: String? = "",
        @SerializedName("anti_money") val antiMoney: String? = "",
        @SerializedName("leveraged_trading") val leveragedTrading: String? = ""
    ): Serializable {
        constructor(): this("", "", "", "", "")
    }
}