package com.fox.one.ex.core

import com.fox.one.ex.core.model.*
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.FoxCall
import com.fox.one.support.framework.network.HttpEngine
import okhttp3.OkHttpClient

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-23
 */
object ExAssetAPI: IExAssetAPI {
    override fun getAssets(): FoxCall<List<AssetInfo>> {
        return apiLoader.load(IExAssetAPI::class.java)
            .getAssets()
    }

    override fun getAsset(assetId: String): FoxCall<AssetResponse> {
        return apiLoader.load(IExAssetAPI::class.java)
            .getAsset(assetId)
    }

    override fun getSnapshots(
        assetId: String?,
        cursor: String?,
        limit: Int,
        order: String
    ): FoxCall<SnapshotArrayResponse> {
        return apiLoader.load(IExAssetAPI::class.java)
            .getSnapshots(assetId, cursor, limit, order)
    }

    override fun getSnapshotDetail(snapshotId: String): FoxCall<SnapshotResponse> {
        return apiLoader.load(IExAssetAPI::class.java)
            .getSnapshotDetail(snapshotId)
    }

    override fun withdraw(request: WithdrawReqBody): FoxCall<SnapshotResponse> {
        return apiLoader.load(IExAssetAPI::class.java)
            .withdraw(request)
    }

    override fun transfer(request: TransferReqBody): FoxCall<SnapshotResponse> {
        return apiLoader.load(IExAssetAPI::class.java)
            .transfer(request)
    }

    var apiLoader = APILoader()

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpEngine.defaultInterceptor)
            .build()

        apiLoader.setOkHttp(okHttpClient)
        apiLoader.setBaseUri(APILoader.BaseUrl(ExchangeAPI.ALPHA_URL, ExchangeAPI.BETA_URL, ExchangeAPI.RELEASE_URL))
    }
}