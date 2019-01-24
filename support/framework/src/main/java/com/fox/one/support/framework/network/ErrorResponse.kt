package com.fox.one.support.framework.network

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-24
 */
class ErrorResponse: BaseResponse() {
    /**
     * 业务状态码，0：成功； 非0：失败，不同状态码对应不同业务错误
     */
    val code = 0
    /**
     * 接口返回的状态信息
     */
    val msg: String? = null

    val hint: String? = null

    override fun code(): Int {
        return code
    }

    override fun isSuccessful(): Boolean {
        return code == 0
    }
}