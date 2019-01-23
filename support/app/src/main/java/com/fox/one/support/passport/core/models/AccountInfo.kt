package com.fox.one.support.passport.core.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-21
 */
data class AccountInfo(@SerializedName("session") var session: Session,
                       @SerializedName("user") var user: User
): BasePassportResponse(), Serializable {
    constructor():this(
        Session(),
        User()
    )

    data class Session(@SerializedName("expired_at") var expired: Long,
                       @SerializedName("key") var key: String,
                       @SerializedName("secret") var secret: String): Serializable {
        constructor(): this(0, "", "")
    }

    data class User(@SerializedName("avatar") var avatar: String,
                    @SerializedName("created_at") var createdAt: Long,
                    @SerializedName("id") var id: Long,
                    @SerializedName("member_id") var memberId: String,
                    @SerializedName("merchant_id") var merchantId: String,
                    @SerializedName("name") var name: String,
                    @SerializedName("phone_code") var countryCode: String,
                    @SerializedName("phone_number") var phoneNumber: String,
                    @SerializedName("push_id") var pushId: String,
                    @SerializedName("updated_at") var updatedAt: Long): Serializable {
        constructor(): this("", 0, 0, "", "", "", "", "", "", 0)
    }
}