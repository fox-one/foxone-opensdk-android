package com.fox.one.ex.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-08
 */
data class AssetIntro(
    @SerializedName("asset_id") val assetId: String = "",
    @SerializedName("chain_id") val chainId: String = "",
    @SerializedName("icon_url") val icon: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("precision") val precision: Int = 0,
    @SerializedName("symbol") val symbol: String = "",
    @SerializedName("type") val type: String = "asset",
    @SerializedName("detail") val detail: Detail = Detail()
) : Serializable {
    constructor() : this("", "", "", "", 0, "", "asset", Detail())

    data class Detail(
        @SerializedName("description") val description: String = "",
        @SerializedName("circulating_supply") val circulatingSupply: Double,
        @SerializedName("explorer_url") val explorerUrl: String = "",
        @SerializedName("ico_date") val icoDate: Long,
        @SerializedName("max_supply") val maxSupply: Double,
        @SerializedName("mineable") val mineable: Boolean,
        @SerializedName("official_url") val officialUrl: String = "",
        @SerializedName("repo_url") val repoUrl: String = "",
        @SerializedName("services_url") val servicesUrl: String = "",
        @SerializedName("total_supply") val totalSupply: Double,
        @SerializedName("white_paper_url") val whitePaperUrl: String = "",
        @SerializedName("wiki_url") val wikiUrl: String = "",
        @SerializedName("tags") val tags: List<Tag> = listOf()
    ) : Serializable {
        constructor() : this("", 0.0, "", 0, 0.0, false, "", "", "", 0.0, "", "", listOf())
    }

    data class Tag(
        @SerializedName("logo") val logo: String = "",
        @SerializedName("tag") val tag: String = "",
        @SerializedName("description") val description: String = ""
    ) : Serializable {
        constructor() : this("", "", "")
    }
}