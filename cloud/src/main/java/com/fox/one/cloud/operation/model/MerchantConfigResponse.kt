package com.fox.one.cloud.operation.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-04-02
 */
data class MerchantConfigResponse(
    @SerializedName("merchant_config")
    val merchantConfig: MerchantInfo = MerchantInfo()
) : Serializable {
    constructor() : this(MerchantInfo())
}