package com.fox.one.ex.core.model

import com.google.gson.annotations.SerializedName

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-23
 */
class SnapshotArrayResponse: ExBaseResponse() {
    @SerializedName("pagination")
    var pagination: Pagination ? = null
    @SerializedName("snapshots")
    var snapshots: List<SnapshotInfo>? = emptyList()
}