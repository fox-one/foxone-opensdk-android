package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.io.StringBufferInputStream

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-14
 */
data class RequestWithdrawReqBody(@SerializedName("asset_id") val assetId: String,
                                  /**
                                   * service = [payment|exchange|otc]
                                   */
                                  @SerializedName("service") val service: String,
                                  @SerializedName("amount") val amount: String,
                                  /**
                                   * for non eos withdraw
                                   */
                                  @SerializedName("public_key") val publicKey: String,
                                  /**
                                   * for eos withdraw
                                   */
                                  @SerializedName("account_name") val accountName: String = "",
                                  /**
                                   * for eos withdraw
                                   */
                                  @SerializedName("account_tag") val accountTag: String = "",
                                  @SerializedName("memo") val memo: String): Serializable {
}