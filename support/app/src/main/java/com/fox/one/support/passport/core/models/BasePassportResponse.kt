package com.fox.one.support.passport.core.models

import com.fox.one.support.framework.network.BaseResponse
import com.google.gson.annotations.SerializedName

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-21
 */
open class BasePassportResponse: BaseResponse() {

    @SerializedName("code")
    private var code: Int = 0

    override fun code(): Int {
        return code
    }

    override fun isSuccessful(): Boolean {
        return code == 0
    }
}