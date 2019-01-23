package com.fox.one.support.passport.core.models

import com.google.gson.annotations.SerializedName

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-21
 */

data class SMSCodeResponse(@SerializedName("token") var token: String): BasePassportResponse() {

}