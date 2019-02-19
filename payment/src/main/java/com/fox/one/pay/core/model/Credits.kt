package com.fox.one.pay.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-02-19
 */
data class Credits(@SerializedName("amount_today") var amountToday: Double,
                   @SerializedName("limit") var limit: Double): Serializable {
}