package com.fox.one.passport.core.model

import com.fox.one.support.framework.network.BaseResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-19
 */
data class KYCProfileInfo (
    /**
     * 创建时间
     */
    @SerializedName("created_at") var createdAt: String,
    /**
     * 真实姓名
     */
    @SerializedName("name") var name: String,
    /**
     * 国家编码, e.g., 86
     */
    @SerializedName("country_code") var countryCode: String,
    /**
     * 卡号，e.g., 身份证号码
     */
    @SerializedName("card_number") var cardNumber: String,
    /**
     * 证件类型， e.g., 1：身份证，2：护照，3：驾驶证
     */
    @SerializedName("document_type") var documentType: Int,
    /**
     * 证件正面照 url地址
     */
    @SerializedName("front") var front: String,
    /**
     * 证件照背面url地址
     */
    @SerializedName("back") var back: String,
    /**
     * 手持证件照url地址
     */
    @SerializedName("together") var together: String,
    /**
     * KYC 状态，参考[KYCStatus]
     */
    @SerializedName("status") var status: Int,
    /**
     * KYC level，参考[KYCLevel]
     */
    @SerializedName("level") var level: Int,
    /**
     * 返回码
     */
    @SerializedName("code") var errorCode: Int,
    /**
     * message
     */
    @SerializedName("message") var message: String): BaseResponse(), Serializable {
    override fun code(): Int {
        return errorCode
    }

    override fun isSuccessful(): Boolean {
        return errorCode == 0
    }

    constructor(): this("", "", "", "", 1, "", "", "", KYCStatus.NOT_YET, KYCLevel.NOT_PASSED, 0, "")
}