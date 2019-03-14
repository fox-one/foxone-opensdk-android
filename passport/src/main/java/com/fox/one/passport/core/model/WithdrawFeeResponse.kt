package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-14
 */
data class WithdrawFeeResponse(@SerializedName("fee_amount") val feeAmount: Double,
                               @SerializedName("fee_asset") val feeAsset: FeeAsset = FeeAsset()): Serializable {

    data class FeeAsset(
        @SerializedName("asset_id") val assetId: String = "",
        @SerializedName("chain_id") val chainId: String = "",
        @SerializedName("change") val change: Double,
        @SerializedName("icon_url") val icon: String = "",
        @SerializedName("name") val name: String = "",
        @SerializedName("price") val price: Double,
        @SerializedName("price_usd") val priceUSD: Double,
        @SerializedName("symbol") val symbol: String
    ): Serializable {
        constructor(): this("", "", 0.0, "", "", 0.0, 0.0, "")
    }
}