package com.fox.one.cloud.operation.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-04-02
 */
data class MerchantInfo(
    @SerializedName("zendesk_domain")
    val zendeskDomain: String = "",
    @SerializedName("zendesk_support_email")
    val zendeskSupportEmail: String = "",
    @SerializedName("zendesk_url")
    val zendeskUrl: String = ""
): Serializable {

    constructor(): this("", "", "")
}