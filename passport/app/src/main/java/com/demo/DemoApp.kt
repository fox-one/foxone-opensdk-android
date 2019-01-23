package com.demo

import android.app.Application
import android.content.Context
import com.fox.one.cloud.FoxSDK
import com.fox.one.passport.core.PassportAPI
import com.fox.one.passport.core.model.AccountInfo
import com.fox.one.support.common.utils.JsonUtils
import com.fox.one.support.common.utils.LogUtils
import com.fox.one.support.framework.Enviroment
import com.fox.one.support.framework.FoxPreference
import com.fox.one.support.framework.imageloader.ImageLoader

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

        FoxSDK.init(this, MERCHANT_ID, FoxSDK.Options(logEnable = true, debugEnable = true, env = Enviroment.ALPHA))

        ImageLoader.setDelegable(ImageLoaderImpl)
        val accountStr = FoxPreference.instance(this).getString("account_info", "")
        PassportAPI.accountInfo = JsonUtils.optFromJson(accountStr, AccountInfo::class.java) ?:
                AccountInfo()

        LogUtils.i("foxone", "${JsonUtils.optToJson(PassportAPI.accountInfo)}")
    }

    companion object {
        fun onLogin(context: Context, accountInfo: AccountInfo) {
            FoxPreference.instance(context).putString("account_info", JsonUtils.optToJson(accountInfo))
            PassportAPI.accountInfo = accountInfo
        }

        fun isLogin(): Boolean {
            return PassportAPI.isLogin()
        }
    }
}