package com.fox.one.pay.core

import com.fox.one.pay.core.model.*
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
object PayAPI: IPayAPI {
    override fun getMainChainAssets(): FoxCall<List<AssetInfo>> {
        return apiLoader.load(IPayAPI::class.java)
            .getMainChainAssets()
    }

    override fun getAssets(): FoxCall<List<AssetInfo>> {
        return apiLoader.load(IPayAPI::class.java)
            .getAssets()
    }

    override fun getAsset(assetId: String): FoxCall<AssetResponse> {
        return apiLoader.load(IPayAPI::class.java)
            .getAsset(assetId)
    }

    override fun getSnapshots(
        assetId: String?,
        cursor: String?,
        limit: Int,
        order: String
    ): FoxCall<SnapshotArrayResponse> {
        return apiLoader.load(IPayAPI::class.java)
            .getSnapshots(assetId, cursor, limit, order)
    }

    override fun getSnapshotDetail(snapshotId: String): FoxCall<SnapshotResponse> {
        return apiLoader.load(IPayAPI::class.java)
            .getSnapshotDetail(snapshotId)
    }

    override fun withdraw(request: WithdrawReqBody): FoxCall<SnapshotResponse> {
        return apiLoader.load(IPayAPI::class.java)
            .withdraw(request)
    }

    override fun transfer(request: TransferReqBody): FoxCall<SnapshotResponse> {
        return apiLoader.load(IPayAPI::class.java)
            .transfer(request)
    }

    override fun transfer(request: ServiceTransferReqBody): FoxCall<SnapshotResponse> {
        return apiLoader.load(IPayAPI::class.java)
            .transfer(request)
    }

    const val ALPHA_URL = "https://dev-gateway.fox.one"
    const val BETA_URL = "https://openapi.fox.one"
    const val RELEASE_URL = "https://openapi.fox.one"

    var apiLoader = APILoader()

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpEngine.defaultInterceptor)
            .build()

        apiLoader.setOkHttp(okHttpClient)
        apiLoader.setBaseUri(APILoader.BaseUrl(ALPHA_URL, BETA_URL, RELEASE_URL))
    }
}