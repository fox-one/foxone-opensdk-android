package com.fox.one.ex.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 币对信息
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-22
 */
data class CoinPairInfo(
    /**
     * 币对 icon url
     */
    @SerializedName("logo") var logo: String,
    /**
     * 币对符号，e.g., EHTBTC
     */
    @SerializedName("symbol") var symbol: String,
    @SerializedName("base_asset_id") var baseAssetId: String,
    /**
     * 基础币，前面部分
     */
    @SerializedName("base_asset") var asset: String,
    @SerializedName("quote_asset_id") var quoteAssetId: String,
    /**
     * 计价币，后面部分
     */
    @SerializedName("quote_asset") var quoteAsset: String,
    /**
     * 价格精度
     */
    @SerializedName("price_precision") var pricePrecision: Int,
    /**
     * 数量精度
     */
    @SerializedName("amount_precision") var amountAssetPrecision: Int,
    /**
     * 基础币最大交易量
     */
    @SerializedName("base_max_amount") var baseMaxAmount: Double = Int.MAX_VALUE.toDouble(),
    /**
     * 基础币最小交易量
     */
    @SerializedName("base_min_amount") var baseMinAmount: Double = 0.0,
    /**
     * 计价币最大交易量
     */
    @SerializedName("quote_max_amount") var quoteMaxAmount: Double = Int.MAX_VALUE.toDouble(),
    /**
     * 计价币最小交易量
     */
    @SerializedName("quote_min_amount") var quoteMinAmount: Double = 0.0,
    /**
     * 币对状态，TRADING or PENDING
     */
    @SerializedName("status") var status: String
) : Serializable {
    constructor() : this("", "","","", "", "", 8, 8, Int.MAX_VALUE.toDouble(), 0.0, Int.MAX_VALUE.toDouble(), 0.0, "")
}