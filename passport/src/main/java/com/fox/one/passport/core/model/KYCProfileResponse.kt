package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-28
 */
data class KYCProfileResponse(@SerializedName("profile") var profile: KYCProfileInfo?): Serializable {
}