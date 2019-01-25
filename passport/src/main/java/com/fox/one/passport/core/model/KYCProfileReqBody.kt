package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * KYC request body
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-19
 */
data class KYCProfileReqBody(
    /**
     * 证件照正面 key，see[UploadResponse.key]
     */
    @SerializedName("front") var front: String?,
    /**
     * 证件照背面 key，see[UploadResponse.key]
     */
    @SerializedName("back") var back: String?,
    /**
     * 手持证件照 key，see[UploadResponse.key]
     */
    @SerializedName("together") var together: String?,
    /**
     * 真实姓名
     */
    @SerializedName("name") var name: String?,
    /**
     * 证件号码
     */
    @SerializedName("card_number") var cardNumber: String?,
    /**
     * 国家编码, e.g., 86
     */
    @SerializedName("country_code") var countryCode: String?,
    /**
     * 证件类型， 1：身份证，2：护照，3：驾驶证
     */
    @SerializedName("document_type") var documentType: Int,
    /**
     * 地址，可选
     */
    @SerializedName("address") var address: String?,
    /**
     * 地址证明，可选
     */
    @SerializedName("address_proof") var addressProof: String?): Serializable {
}