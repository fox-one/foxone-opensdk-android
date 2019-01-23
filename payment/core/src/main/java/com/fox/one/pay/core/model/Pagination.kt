package com.fox.one.pay.core.model

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Created by zhangyinghao on 2018/5/1.
 */
class Pagination : Serializable {
    /**
     * nextCursor : MmJhfDVhZGY0Yzhj
     * hasNext : true
     */

    @SerializedName("next_cursor")
    var nextCursor: String? = null

    @SerializedName("has_next")
    var hasNext = false
}
