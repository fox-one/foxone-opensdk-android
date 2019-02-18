package com.fox.one.pay.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-02-18
 */
data class ServiceTransferReqBody(
    @SerializedName("to_member") var toMemberId: String?,
    @SerializedName("service") var service: String?,
    @SerializedName("asset_id") var assetId: String,
    @SerializedName("amount") var amount: String,
    @SerializedName("memo") var memo: String,
    @SerializedName("trace_id") var traceId: String): Serializable {
    constructor():this("", "", "", "0", "", "")
}