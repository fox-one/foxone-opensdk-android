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
    @SerializedName("price_usd") var PriceUsd: Double = 0.0,
    @SerializedName("change") var changePercent: Double = 0.0,
    @SerializedName("precision") var precision: Int = 8,
    @SerializedName("chain_id") var chainId: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("symbol") var symbol: String?,
    @SerializedName("icon_url") var icon: String?,
    @SerializedName("public_key") var publicKey: String?,
    @SerializedName("account_name") var accountName: String?,
    @SerializedName("account_tag") var accountTag: String?): Serializable {
    constructor(): this("", 0.0, 0.0, 0.0, 0.0, 8, "", "", "", "", "", "", "")

    fun isEOS(): Boolean {
        return symbol == "EOS"
    }

    companion object {
        var decimalFormat = DecimalFormat("##################0.########")
    }
}