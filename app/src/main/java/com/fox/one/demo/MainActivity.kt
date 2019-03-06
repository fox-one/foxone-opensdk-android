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
import com.fox.one.support.framework.APPLifeCycleManager
import com.fox.one.support.framework.network.ErrorResponse
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
//            val str = "{\n" +
//                    "  \"code\": 1110,\n" +
//                    "  \"msg\": \"2-Step verification required\",\n" +
//                    "  \"data\": {\n" +
//                    "    \"tfa_token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidHAiOiIyZmEgbG9naW4iLCJleHAiOjE1NTExNjc4NzB9.4up5L4315b7UVCrJddBg_6TbYIBS5KGHy0AHYG6_ZiU\"\n" +
//                    "  }\n" +
//                    "}"
//
//            val data = JsonUtils.optFromJson(str, ErrorResponse::class.java)
//
//            LogUtils.i("foxone", "tfa_token: ${data.data?.get("tfa_token").toString()}")

            DEVAPI.Impl.requestDevices()
                .enqueue(object: Callback<Any> {
                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        HttpErrorHandler.handle(t)
                    }

                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        LogUtils.i("foxone", "devices:::${JsonUtils.optToJson(response.body())}")
                    }

                })
        }

        LogUtils.i("foxone", "brand:${Build.BRAND}, board:${Build.BOARD}, device:${Build.DEVICE}, model:${Build.MODEL}, " +
                "display:${Build.DISPLAY}, bootloader:${Build.BOOTLOADER}, fingerprint:${Build.FINGERPRINT}, " +
                "hardware:${Build.HARDWARE}, product:${Build.PRODUCT}, id:${Build.ID}, host:${Build.HOST}")
        LogUtils.i("foxone", " radioVersion:${Build.getRadioVersion()}, " +
                "                release: ${Build.VERSION.RELEASE}, base_os: ${Build.VERSION.BASE_OS}, sdk_int: ${Build.VERSION.SDK_INT}, codename: ${Build.VERSION.CODENAME}")
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
}
