package com.fox.one.support

import android.app.Application
import android.content.Context
import com.fox.one.support.common.utils.JsonUtils
import com.fox.one.support.common.utils.LogUtils
import com.fox.one.support.framework.FoxPreference
import com.fox.one.support.framework.FoxRuntime
import com.fox.one.support.framework.imageloader.ImageLoader
import com.fox.one.support.passport.core.PassportAPI
import com.fox.one.support.passport.core.models.AccountInfo

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-21
 */
class DemoApp: Application() {

    override fun onCreate() {
        super.onCreate()

        FoxRuntime.init(this)
        FoxRuntime.debug = true
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