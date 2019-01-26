package com.fox.one.pay.core

import com.fox.one.pay.core.model.*
import com.fox.one.support.framework.network.FoxCall
import retrofit2.http.*

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-23
 */
interface IPayAPI {
    /**
     * 获取用户的所有资产（资产列表）
     */
    @GET("/member/payment/assets")
    fun getAssets(): FoxCall<List<AssetInfo>>

    /**
     * 获取支持转入的资产列表
     */
    @GET("/member/payment/assets?chain=1")
    fun getDepositableAssets(): FoxCall<List<AssetInfo>>

    /**
     * 获取单个资产详情
     */
    @GET("/member/payment/asset/{assetId}")
    fun getAsset(@Path("assetId") assetId: String): FoxCall<AssetResponse>

    /**
     * 转账记录(列表) 。
     * [assetId]: 传null或者空字符串时，获取所有的转账记录。
     * [order]: "ASC", "DESC"
     */
    @GET("/member/payment/snapshots")
    fun getSnapshots(@Query("asset_id") assetId: String?, @Query("cursor") cursor: String?, @Query("limit") limit: Int = 20, @Query("order") order: String = "DESC"): FoxCall<SnapshotArrayResponse>

    @GET("/member/payment/snapshot/{snapshotId}")
    fun getSnapshotDetail(@Path("snapshotId") snapshotId: String): FoxCall<SnapshotResponse>

    /**
     * 提现接口(转出)
     */
    @POST("/member/payment/withdraw")
    fun withdraw(@Body request: WithdrawReqBody): FoxCall<SnapshotResponse>

    @POST("/member/payment/transfer")
    fun transfer(@Body request: TransferReqBody): FoxCall<SnapshotResponse>
}