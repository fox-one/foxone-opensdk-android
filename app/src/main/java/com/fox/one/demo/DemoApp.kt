package com.fox.one.demo

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.util.TypedValue
import com.fox.one.cloud.FoxSDK
import com.fox.one.passport.core.model.KYCStatus
import com.fox.one.support.common.utils.LogUtils
import com.fox.one.support.common.utils.ToastUtil
import com.fox.one.support.framework.APPLifeCycleManager
import com.fox.one.support.framework.AppLifecycleCallback
import com.fox.one.support.framework.Enviroment
import com.fox.one.support.framework.network.APILoader
import com.foxone.exchange.ex.ExModule
import com.foxone.exchange.framework.app.F1EXTheme

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-21
 */
class DemoApp: Application() {

    private val MERCHANT_ID = "5c8a9491dca25af694004d5e1711b217"

    override fun onCreate() {
        super.onCreate()

        //初始Fox SDK
        FoxSDK.init(this, MERCHANT_ID, FoxSDK.Options(logEnable = true, debugEnable = true, env = Enviroment.ALPHA,
            customBaseUrl = APILoader.BaseUrl("https://www.fox.one", "https://www.fox.one", "https://www.fox.one")))

        //初始化交易模块（带UI）
        ExModule.init(this)
        ExModule.setOnKYCCheckListener { context, status ->
            when(status) {
                KYCStatus.NOT_YET -> {
                    ToastUtil.show(context, "kyc not yet")
                }
                KYCStatus.HAVE_CREATED -> {
                    ToastUtil.show(context, "kyc been created")
                }
                KYCStatus.IN_PROGRESS -> {
                    ToastUtil.show(context, "kyc in progress")
                }
                KYCStatus.AUTO_VERIFICATION_PASSED -> {
                    ToastUtil.show(context, "kyc auto verification passed")
                }
                KYCStatus.HUMAN_VERIFICATION_PASSED -> {
                    ToastUtil.show(context, "kyc human verification passed")
                }
                KYCStatus.REJECTED -> {
                    ToastUtil.show(context, "kyc rejected")
                }
            }
        }

        ExModule.setOnLaunchLoginUIListener {
            LoginActivity.start(it)
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        MultiDex.install(this)
    }
}