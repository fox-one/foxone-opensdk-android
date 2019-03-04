package com.fox.one.demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.fox.one.passport.core.PassportAPI
import com.fox.one.passport.core.model.*
import com.fox.one.support.common.utils.JsonUtils
import com.fox.one.support.common.utils.LogUtils
import com.fox.one.support.framework.imageloader.ImageLoader
import com.fox.one.support.framework.network.HttpErrorHandler
import com.foxone.exchange.framework.account.AccountManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-21
 */
class SMSLoginActivity: AppCompatActivity() {

    private var captchaId: String = ""
    private var smsToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sms_login)

        val capV = findViewById<ImageView>(R.id.iv_captcha)
        capV.setOnClickListener {
            PassportAPI.requestCaptcha().enqueue(object: Callback<CaptchaInfo> {
                override fun onFailure(call: Call<CaptchaInfo>, t: Throwable) {
                    HttpErrorHandler.handle(t)
                }

                override fun onResponse(call: Call<CaptchaInfo>, response: Response<CaptchaInfo>) {
                    captchaId = response.body()?.captchaId ?: ""
                    ImageLoader.display(capV, PassportAPI.getCaptchaUrl(captchaId))
                }
            })
        }

        findViewById<Button>(R.id.btn_req_sms_code).setOnClickListener {
            val phone = findViewById<EditText>(R.id.edt_phone).text.toString()
            val cap = findViewById<EditText>(R.id.edt_captcha).text.toString()

            PassportAPI.requestCodeOfSMSLogin(
                SMSCodeReqBody(
                    countryCode = "86",
                    phoneNumber = phone,
                    captchaId = captchaId,
                    captcha = cap
                )
            )
                .enqueue(object: Callback<ValidCodeResponse> {
                    override fun onFailure(call: Call<ValidCodeResponse>, t: Throwable) {
                        HttpErrorHandler.handle(t)
                    }

                    override fun onResponse(call: Call<ValidCodeResponse>, response: Response<ValidCodeResponse>) {
                        smsToken = response.body()?.token ?: ""
                    }
                })
        }

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            val code = findViewById<EditText>(R.id.edt_sms_code).text.toString()
            PassportAPI.login(
                LoginWithSMSReqBody(
                    token = smsToken,
                    smsCode = code
                )
            ).enqueue(object: Callback<AccountInfo> {
                override fun onFailure(call: Call<AccountInfo>, t: Throwable) {
                    HttpErrorHandler.handle(t)
                }

                override fun onResponse(call: Call<AccountInfo>, response: Response<AccountInfo>) {
                    AccountManager.login(response.body() ?: AccountInfo())
                    LogUtils.i("foxone", "sms login: ${JsonUtils.optToJson(response.body())}")
                    finish()
                }
            })
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SMSLoginActivity::class.java))
        }
    }
}