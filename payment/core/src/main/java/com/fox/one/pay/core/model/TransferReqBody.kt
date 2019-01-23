package com.fox.one.pay.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-23
 */
data class TransferReqBody(
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

    @SerializedName("opponent_id") val opponentId: String,
    @SerializedName("member_id") val memberId: String): Serializable {

}