package com.fox.one.ex.core.model

import com.google.gson.annotations.SerializedName

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-07
 */
class SnapshotResponse: ExBaseResponse() {

    @SerializedName("snapshot")
    val snapshot: SnapshotInfo? = SnapshotInfo()
}