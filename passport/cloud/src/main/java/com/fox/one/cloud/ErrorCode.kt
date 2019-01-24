package com.fox.one.cloud

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-10
 */
object ErrorCode {

    //system
    /**
     * 缺失参数
     */
    const val SYS_LACK_PARAM = 10001
    /**
     * 禁止访问数据
     */
    const val SYS_FORBIDDEN = 10002
    /**
     * 无效的数据
     */
    const val SYS_INVALID_DATA = 10003

    //account
    /**
     * 商户授权信息校验失败
     */
    const val ACCOUNT_MERCHANT_AUTH_FAIL = 10101
    /**
     * 手机号已注册
     */
    const val ACCOUNT_PHONE_REGISTED = 10102
    /**
     * 邮箱已注册
     */
    const val ACCOUNT_EMAIL_REGISTED = 10103
    /**
     * 账号无法注册
     */
    const val ACCOUNT_REGISTER_FORBID = 10104
    /**
     * 账号未注册
     */
    const val ACCOUNT_NOT_REGISTED = 10105
    /**
     * 用户授权信息校验失败
     */
    const val ACCOUNT_AUTH_FAIL = 10106
    /**
     * 登录失败：未注册或密码错误
     */
    const val ACCOUNT_LOGIN_FAIL = 10107
    /**
     * 密码错误
     */
    const val ACCOUNT_WRONG_PASSWORD = 10108
    /**
     * 禁止访问数据，e.g.,被拉黑名单
     */
    const val ACCOUNT_ACTION_BLOCKED = 10109
    /**
     * 重复请求
     */
    const val ACCOUNT_REPLAY_REQUEST = 10110

    //validation code
    /**
     * 验证码错误
     */
    const val VALIDATION_WRONG_CODE = 10201
    /**
     * 验证token无效
     */
    const val VALIDATION_INVALID_TOKEN = 10202
    /**
     * 图形验证码失效
     */
    const val VALIDATION_INVALID_CAPTCHA = 10203

    //kyc
    /**
     * kyc等级不够
     */
    const val KYC_HIGHER_LEVEL_REQUIRED = 10501
    /**
     * 信息不全
     */
    const val KYC_INCOMPLETE_INFO = 10502
    /**
     * 文件不全
     */
    const val KYC_INCOMPLETE_DOCS = 10503
    /**
     * 正面照不符合要求
     */
    const val KYC_INVALID_FRONT_CARD = 10504
    /**
     * 名字不匹配
     */
    const val KYC_NAME_NOT_MATCH = 10505
    /**
     * 证件号不匹配
     */
    const val KYC_CARD_NUMBER_NOT_MATCH = 10506
    /**
     * 背面照不符合要求
     */
    const val KYC_INVALID_BACKEND_CARD = 10507
    /**
     * 手持证件照不符合要求
     */
    const val KYC_TWO_FACES_REQUIRED = 10508
    /**
     * 不是同一个人
     */
    const val KYC_NOT_THE_SAME_PERSON = 10509
    /**
     * 人工审核被拒
     */
    const val KYC_REJECTED = 10510
    /**
     * 个人信息校验失败
     */
    const val KYC_INVALID_PROFILE = 10511

    //wallet
    /**
     * 数量太小
     */
    const val WALLET_AMOUNT_TOO_SMALL = 1160
    /**
     * 余额不足
     */
    const val WALLET_INSUFFICIENT_BALANCE = 1161
    /**
     * 无效的trace id
     */
    const val WALLET_INVALID_TRACE_ID = 1162

    //exchange
    /**
     * 无效的symbol
     */
    const val EX_INVALID_SYMBOL = 20001
    /**
     * 无效的时间刻度
     */
    const val EX_INVALID_INTERVAL = 20002

    //ws
    const val WS_UNKNOWN_SYMBOL = 10010
    const val WS_UNKNOWN_EVENT = 10011
    const val WS_UNKNOWN_ACTION = 10012
    const val WS_UNKNOWN_MARKET = 10013
    const val WS_UNKNOWN_KLINE_INTERVAL = 10022
    const val WS_UNKNOWN_DEPTH_LEVEL = 10023
}