package com.fox.one.ex.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-08
 */
data class AssetIntroResponse(@SerializedName("asset") val assetIntro: AssetIntro = AssetIntro()): Serializable {
}