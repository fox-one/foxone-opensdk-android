package com.fox.one.pay.core.model

import com.fox.one.support.framework.network.BaseResponse

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-23
 */
open class PayBaseResponse: BaseResponse() {

    private var code = 0

    override fun code(): Int {
        return code
    }

    override fun isSuccessful(): Boolean {
        return code == 0
    }
}