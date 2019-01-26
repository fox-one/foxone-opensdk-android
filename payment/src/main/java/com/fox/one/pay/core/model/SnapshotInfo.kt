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
    @SerializedName("snapshot_id") var snapshotId: String?,
    @SerializedName("trace_id") var traceId: String?,
    @SerializedName("wallet_id") var walletId: String?,
    @SerializedName("asset_id") var assetId: String?,
    @SerializedName("opponent_id") var opponentId: String?,
    @SerializedName("source") var source: String?,
    @SerializedName("memo") var memo: String?,
    @SerializedName("member_id") var memberId: String?,
    @SerializedName("service") var service: String?,
    @SerializedName("label") var label: String?,
    @SerializedName("data") var data: Any?,
    @SerializedName("created_at") var createdAt: Long,
    @SerializedName("amount") var amount: Double,
    @SerializedName("asset") var asset: AssetInfo?): Serializable {
    constructor(): this("", "", "", "", "","", "", "", "", "", null, 0L, 0.0, AssetInfo())
}