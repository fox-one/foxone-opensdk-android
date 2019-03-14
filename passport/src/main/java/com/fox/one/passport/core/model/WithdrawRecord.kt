package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.security.PublicKey

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-14
 */
data class WithdrawRecord(@SerializedName("account_name") val accountName: String = "",
                          @SerializedName("account_tag") val accountTag: String = "",
                          @SerializedName("amount") val amount: String = "0",
                          @SerializedName("amount_cny") val amountCNY: String = "0",
                          @SerializedName("amount_usd") val amountUSD: String = "0",
                          @SerializedName("asset_id") val assetId: String = "",
                          @SerializedName("created_at") val createdAt: Long,
                          @SerializedName("fail_cause") val failCause: String = "",
                          @SerializedName("id") val id: Long,
                          @SerializedName("memo") val memo: String = "",
                          @SerializedName("public_key") val publicKey: String = "",
                          @SerializedName("service") val service: String = "",
                          @SerializedName("state") val state: Int): Serializable {

    constructor(): this("", "", "0", "0", "0", "", 0L, "", 0L, "", "", "", STATE_ERROR)
    companion object {
        /**
         * 需要验证手机号，邮箱等
         */
        const val STATE_NEED_SMS_EMAIL_AUTH = 1
        /**
         * 等待管理员审核通过
         */
        const val STATE_WAIT_FOR_APROVE = 2
        /**
         * 提现失败
         */
        const val STATE_ERROR = 3
        /**
         * 提现成功
         */
        const val STATE_SUCCEED = 4
    }
}