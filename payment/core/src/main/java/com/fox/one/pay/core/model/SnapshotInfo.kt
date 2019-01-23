package com.fox.one.pay.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 账单信息
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-01
 */
data class SnapshotInfo(
    @SerializedName("snapshot_id") val snapshotId: String?,
    @SerializedName("trace_id") val traceId: String?,
    @SerializedName("wallet_id") val walletId: String?,
    @SerializedName("asset_id") val assetId: String?,
    @SerializedName("opponent_id") val opponentId: String?,
    @SerializedName("source") val source: String?,
    @SerializedName("memo") val memo: String?,
    @SerializedName("member_id") val memberId: String?,
    @SerializedName("service") val service: String?,
    @SerializedName("label") val label: String?,
    @SerializedName("data") val data: Any?,
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("amount") val amount: Double,
    @SerializedName("asset") val asset: AssetInfo.AssetBasic?): Serializable {
    constructor(): this("", "", "", "", "","", "", "", "", "", null, 0L, 0.0, AssetInfo.AssetBasic())
}