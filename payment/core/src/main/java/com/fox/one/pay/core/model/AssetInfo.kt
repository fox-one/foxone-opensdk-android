package com.fox.one.pay.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.DecimalFormat

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-23
 */
data class AssetInfo(
    @SerializedName("asset_id") val assetId: String,
    @SerializedName("balance") val balance: Double,
    /**
     * 资产价格，以CNY为计价单位
     */
    @SerializedName("price") var price: Double = 0.0,
    @SerializedName("transaction_amount") val transactionAmount: Double,
    @SerializedName("transaction_count") val transactionCount: Double,
    @SerializedName("asset") val basic: AssetBasic,
    @SerializedName("address") val address: Address): Serializable {
    constructor(): this("", 0.0, 0.0, 0.0, 0.0, AssetBasic(), Address())

    fun isEOS(): Boolean {
        return basic.symbol == "EOS"
    }

    data class AssetBasic(
        @SerializedName("asset_id") val assetId: String,
        @SerializedName("asset_key") val assetKey: String,
        @SerializedName("chain_id") val chainId: String,
        @SerializedName("name") val name: String,
        @SerializedName("symbol") val symbol: String,
        @SerializedName("icon_url") val icon: String,
        @SerializedName("precision") val precision: Int = 0
    ): Serializable {
        constructor(): this("", "", "", "", "", "")
    }

    data class Address(
        @SerializedName("user_id") val userId: String,
        @SerializedName("chain_id") val chainId: String,
        @SerializedName("public_key") val publicKey: String,
        @SerializedName("account_name") val accountName: String,
        @SerializedName("account_tag") val accountTag: String,
        @SerializedName("confirmations") val confirmations: Double,
        @SerializedName("capitalization") val capitalization: Double
    ): Serializable {
        constructor(): this("","", "", "", "", 0.0, 0.0)
    }

    companion object {
        var decimalFormat = DecimalFormat("##################0.########")
    }
}