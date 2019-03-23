package com.fox.one.demo

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import com.fox.one.ex.core.stream.AllTickerStreamObserver
import com.fox.one.ex.core.stream.StreamDataManager
import com.fox.one.ex.core.stream.model.AllTickerStreamInfo
import com.fox.one.ex.core.stream.model.AllTickerStreamReqBody
import com.fox.one.ex.core.stream.model.StreamAction
import com.fox.one.passport.core.PassportAPI
import com.fox.one.passport.core.model.AccountInfo
import com.fox.one.passport.core.model.BasePassportResponse
import com.fox.one.pay.core.OtcAPI
import com.fox.one.support.common.extension.getColorCompat
import com.fox.one.support.common.utils.JsonUtils
import com.fox.one.support.common.utils.LogUtils
import com.fox.one.support.common.utils.maskMiddle
import com.fox.one.support.framework.APPLifeCycleManager
import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.ErrorResponse
import com.fox.one.support.framework.network.FoxCall
import com.fox.one.support.framework.network.HttpErrorHandler
import com.foxone.exchange.account.kyc.KYCActivity
import com.foxone.exchange.ex.ExModule
import com.foxone.exchange.ex.wallet.WalletActivity
import com.foxone.exchange.ex.wallet.transfer.TransferActivity
import com.foxone.exchange.framework.account.AccountManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LogUtils.i("foxone", "MainActivity.onCreate")
        findViewById<Button>(R.id.btn_register).setOnClickListener {
            RegisterActivity.start(this@MainActivity)
        }

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            LoginActivity.start(this@MainActivity)
        }

        findViewById<Button>(R.id.btn_sms_code).setOnClickListener {
            SMSLoginActivity.start(this@MainActivity)
        }

        findViewById<Button>(R.id.btn_user_info).setOnClickListener {
            if (AccountManager.isLogin()) {
                PassportAPI.getAccountInfo().enqueue(object: Callback<AccountInfo> {
                    override fun onFailure(call: Call<AccountInfo>, t: Throwable) {
                        HttpErrorHandler.handle(t)
                        if (t is HttpException) {
                            LogUtils.i("foxone", "httpcode:${t.response().code()}, msg: ${t.response().errorBody()?.string()}")
                        } else {
                            LogUtils.i("foxone", "${t.toString()}")
                        }
                    }

                    override fun onResponse(call: Call<AccountInfo>, response: Response<AccountInfo>) {
                        LogUtils.i("foxone", "${JsonUtils.optToJson(response.body())}")
                    }

                })
            } else {
                AccountManager.launchLoginUI(this@MainActivity)
            }
        }

        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            PassportAPI.logout()
                .enqueue(object: Callback<BasePassportResponse> {
                    override fun onFailure(call: Call<BasePassportResponse>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<BasePassportResponse>,
                        response: Response<BasePassportResponse>
                    ) {
                        AccountManager.logout()
                        ExModule.cleanCache()
                    }
                })
        }

        findViewById<Button>(R.id.btn_exchange).setOnClickListener {
            ExchangeActivity.start(this@MainActivity)
        }

        findViewById<Button>(R.id.btn_wallet).setOnClickListener {
            if (AccountManager.isLogin()) {
                WalletActivity.start(this@MainActivity)
            } else {
                AccountManager.launchLoginUI(this@MainActivity)
            }
        }

        findViewById<Button>(R.id.btn_transfer).setOnClickListener {
            if (AccountManager.isLogin()) {
                TransferActivity.start(this@MainActivity)
            } else {
                AccountManager.launchLoginUI(this@MainActivity)
            }
        }

        findViewById<Button>(R.id.btn_kyc).setOnClickListener {
//            if (AccountManager.isLogin()) {
//                ExModule.startKYCActivity(this@MainActivity)
//            } else {
//                AccountManager.launchLoginUI(this@MainActivity)
//            }
            val apiLoader = APILoader()

            val secret = "6Lfe0pgUAAAAALdVZMJkeCUXhcGvXQY0gCQ55Ysq"
            val response = "03AOLTBLRqoqhHDZcFAJBqqSLWsvaFxlzIGqbKPu9w-bZhFjAB-0tYMYAUex2PMn-K4vmj2Z4DFErMCzY2BQmFLIvYhkkn4V4ZYL5uXL9-B6VGNtsXuv80821DTGT-D3aXplGkpTFlGjMbFNRZWisok6htSwAYKQZpuFY0qrmULjOwO5dbJ8XhCWW0g6ronLGpIgu5XtE-r0s-oAOwx05FtZ2kKfISJuouq-sjJXs6lt3DtHUBhrvJzn2gtpcy61L5U_wZBDUmPOOTPSt8JA8bLqz4PIDZKBs7QCR4Z0k7Bm62pu5awe-oNIuntVF174S92hfIXQO3fNz0rI1tSfJLzQx2FS8hGU1qPw"

            apiLoader.setBaseUri(APILoader.BaseUrl("https://www.google.com", "https://www.google.com", "https://www.google.com"))
            apiLoader.load(SiteVerifyAPI::class.java)
                .verify(secret, response)
                .enqueue(object: Callback<Any> {
                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        LogUtils.i("foxone", "exception:${t.toString()}")
                    }

                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        LogUtils.i("foxone", "${JsonUtils.optToJson(response.body())}")
                    }
                })
        }

        LogUtils.i("foxone", "day: ${System.currentTimeMillis() + (1000 * 60 * 60 * 24)}")

        findViewById<Button>(R.id.webView).setOnClickListener {
            WebViewActivity.start(this@MainActivity)
        }
    }

    private var subIdOfAllTicker: String = ""
    override fun onResume() {
        super.onResume()

        subIdOfAllTicker = UUID.randomUUID().toString()
        StreamDataManager.subscribe(object: AllTickerStreamObserver(AllTickerStreamReqBody(subIdOfAllTicker, StreamAction.SUB.key)) {
            override fun onUpdate(data: AllTickerStreamInfo) {
//                LogUtils.i("foxone", "stream:::${JsonUtils.optToJson(data)}")
            }
        })
    }

    override fun onPause() {
        super.onPause()
        StreamDataManager.unsubscribe(AllTickerStreamReqBody(subIdOfAllTicker, StreamAction.UNSUB.key))
    }

    interface SiteVerifyAPI {

        @POST("/recaptcha/api/siteverify")
        fun verify(@Query("secret") secret: String, @Query("response") response: String): FoxCall<Any>
    }
}
