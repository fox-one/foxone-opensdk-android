package com.fox.one.passport.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-11-28
 */
data class EmailCodeReqBody(
    /**
     * 邮箱地址
     */
    @SerializedName("email") val email: String): Serializable