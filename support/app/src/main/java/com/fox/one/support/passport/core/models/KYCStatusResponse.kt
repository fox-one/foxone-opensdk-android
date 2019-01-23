package com.foxone.exchange.account.core.model

import com.fox.one.support.passport.core.models.BasePassportResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-19
 */
data class KYCStatusResponse(@SerializedName("status") var status: Int): BasePassportResponse(), Serializable {
}