package com.fox.one.passport.core.model

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-19
 */
enum class DocumentType(value: Int) {
    /**
     * 身份证
     */
    IDENTITY(1),
    /**
     * 护照
     */
    PASSPORT(2),
    /**
     * 驾驶证
     */
    DRIVING_LICENSE(3);

    val value = value
}