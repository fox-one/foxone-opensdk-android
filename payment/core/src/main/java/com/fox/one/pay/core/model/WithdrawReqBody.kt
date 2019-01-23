package com.fox.one.pay.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-28
 */
data class WithdrawReqBody(
    /**
     * UUID
     */
    @SerializedName("trace_id") val traceId: String,
    /**
     * 转账数量
     */
    @SerializedName("amount") val amount: String,
    /**
     * 资产ID
     */
    @SerializedName("asset_id") val assetId: String,
    /**
     * 备注，对于eos来说, memo 即是[AssetInfo.accountTag]
     */
    @SerializedName("memo") val memo: String,
    /**
     * 对于eos来说， publicKey即是[AssetInfo.accountName]
     */
    @SerializedName("public_key") val assetAddress: String,
    @SerializedName("account_name") val accountName: String,
    @SerializedName("account_tag") val accountTag: String,
    @SerializedName("member_id") val memberId: String): Serializable