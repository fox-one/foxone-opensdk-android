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
    @SerializedName("asset_id") var assetId: String?,
    @SerializedName("balance") var balance: Double,
    /**
     * 资产价格，以CNY为计价单位
     */
    @SerializedName("price") var price: Double = 0.0,
    @SerializedName("transaction_amount") var transactionAmount: Double,
    @SerializedName("transaction_count") var transactionCount: Double,
    @SerializedName("asset") var basic: AssetBasic?,
    @SerializedName("address") var address: Address?): Serializable {
    constructor(): this("", 0.0, 0.0, 0.0, 0.0, AssetBasic(), Address())

    fun isEOS(): Boolean {
        return basic?.symbol == "EOS"
    }

    data class AssetBasic(
        @SerializedName("asset_id") var assetId: String?,
        @SerializedName("asset_key") var assetKey: String?,
        @SerializedName("chain_id") var chainId: String?,
        @SerializedName("name") var name: String?,
        @SerializedName("symbol") var symbol: String?,
        @SerializedName("icon_url") var icon: String?,
        @SerializedName("precision") var precision: Int = 0
    ): Serializable {
        constructor(): this("", "", "", "", "", "")
    }

    data class Address(
        @SerializedName("user_id") var userId: String?,
        @SerializedName("chain_id") var chainId: String?,
        @SerializedName("public_key") var publicKey: String?,
        @SerializedName("account_name") var accountName: String?,
        @SerializedName("account_tag") var accountTag: String?,
        @SerializedName("confirmations") var confirmations: Double,
        @SerializedName("capitalization") var capitalization: Double
    ): Serializable {
        constructor(): this("","", "", "", "", 0.0, 0.0)
    }

    companion object {
        var decimalFormat = DecimalFormat("##################0.########")
    }
}