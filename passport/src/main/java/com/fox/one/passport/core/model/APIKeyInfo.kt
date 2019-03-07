package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-07
 */
data class APIKeyInfo(
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("expired_at") val expiredAt: Long,
    @SerializedName("ip") val ips: List<String>? = listOf(),
    @SerializedName("is_expired") val isExpired: Boolean,
    @SerializedName("key") val key: String,
    @SerializedName("label") val label: String,
    @SerializedName("secret") val secret: String
): Serializable {
}