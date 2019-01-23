package com.foxone.exchange.account.core.model

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-19
 */
object KYCStatus {
    /**
     * 还未申请身份认证
     */
    const val NOT_YET = 0
    /**
     * 已申请身份认证
     */
    const val HAVE_CREATED = 1
    /**
     * 身份认证处理中
     */
    const val IN_PROGRESS = 2
    /**
     * 自动审核通过
     */
    const val AUTO_VERIFICATION_PASSED = 3
    /**
     * 人工审核通过
     */
    const val HUMAN_VERIFICATION_PASSED = 4
    /**
     * 身份认证未通过
     */
    const val REJECTED = 5
    /**
     * 身份认证已通过
     */
    const val APPROVED = 6
}