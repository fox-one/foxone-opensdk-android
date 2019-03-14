package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-14
 */
data class WithdrawFeeReqBody(@SerializedName("asset_id") val assetId: String,
                              /**
                               * service = [payment|exchange|otc]
                               */
                              @SerializedName("service") val service: String,
                              @SerializedName("public_key") val publicKey: String,
                              @SerializedName("account_name") val accountNAme: String,
                              @SerializedName("account_tag") val accountTag: String): Serializable {
}