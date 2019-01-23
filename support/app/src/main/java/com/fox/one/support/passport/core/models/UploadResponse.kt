package com.fox.one.support.passport.core.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-19
 */
data class UploadResponse(
    /**
     * 图片标识
     */
    @SerializedName("key") var key: String,
    /**
     * 图片url地址
     */
    @SerializedName("view") var view: String): Serializable {
}