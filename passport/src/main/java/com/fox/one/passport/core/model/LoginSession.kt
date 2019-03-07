package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-07
 */
data class LoginSession(
    @SerializedName("created_at") val createdAt: Long = 0L,
    @SerializedName("expired_at") val expiredAt: Long = 0L,
    @SerializedName("is_expired") val isExpired: Boolean = false,
    @SerializedName("key") val key: String = "",
    @SerializedName("device") val deviceInfo: DeviceInfo?
) : Serializable {

    data class DeviceInfo(
        @SerializedName("id") val id: Long,
        @SerializedName("name") val name: String?,
        @SerializedName("platform") val platform: String?,
        @SerializedName("ip") val ip: String?,
        @SerializedName("ua") val ua: String?
    ) : Serializable {

    }
}