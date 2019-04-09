package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-14
 */
data class WithdrawStatus(@SerializedName("is_blocked") val isBlocked: Boolean,
                          @SerializedName("block_until") val blockUntil: Long,
                          @SerializedName("block_cause") val blockCause: String?,
                          @SerializedName("kyc_status") val kycStatus: Int,
                          @SerializedName("is_tfa_enabled") val isTFAEnabled: Boolean,
                          @SerializedName("remain_amount_btc") val remainAmountBTC: Double,
                          @SerializedName("max_amount_btc") val maxAmountBTC: Double,
                          @SerializedName("max_single_amount_btc") val maxSingleAmountBTC: Double): Serializable {
    constructor(): this(false, 0, "", KYCStatus.APPROVED, false, 0.0, 0.0, 0.0)
}