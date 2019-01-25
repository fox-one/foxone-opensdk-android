package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-21
 */

data class ValidCodeResponse(@SerializedName("token") var token: String): BasePassportResponse() {

}