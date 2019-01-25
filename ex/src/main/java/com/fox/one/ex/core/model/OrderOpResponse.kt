package com.fox.one.ex.core.model

import com.fox.one.ex.core.model.ExBaseResponse
import com.fox.one.pay.core.model.SnapshotInfo
import com.google.gson.annotations.SerializedName

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-12-06
 */
class OrderOpResponse: ExBaseResponse() {

    @SerializedName("snapshot")
    var snapshot: SnapshotInfo? = SnapshotInfo()
}