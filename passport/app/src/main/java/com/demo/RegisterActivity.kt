package com.demo

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-21
 */
class RegisterActivity: AppCompatActivity() {

    private var captchaId = ""
    private var smsToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)
        val captchaV = findViewById<ImageView>(R.id.iv_captcha)
        captchaV.setOnClickListener {
            PassportAPI.requestCaptcha()
                .enqueue(object: Callback<CaptchaInfo> {
                    override fun onFailure(call: Call<CaptchaInfo>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<CaptchaInfo>, response: Response<CaptchaInfo>) {
                        captchaId = response.body()?.captchaId ?: ""
                        val url = PassportAPI.getCaptchaUrl(captchaId)
                        LogUtils.i("foxone", "captchaId:${captchaId}, url:${url}")

                        ImageLoader.display(captchaV, url)
                    }
                })
        }

        findViewById<Button>(R.id.btn_req_sms_code).setOnClickListener {
            val cap = findViewById<EditText>(R.id.edt_captcha).text.toString()
            val phone = findViewById<EditText>(R.id.edt_phone).text.toString()

            PassportAPI.requestValidCodeOfRegister(
                SMSCodeReqBody(
                    captcha = cap,
                    captchaId = captchaId,
                    countryCode = "86",
                    phoneNumber = phone
                )
            )
                .enqueue(object: Callback<ValidCodeResponse> {
                    override fun onFailure(call: Call<ValidCodeResponse>, t: Throwable) {
                        LogUtils.i("foxone", "${t.toString()}")
                    }

                    override fun onResponse(call: Call<ValidCodeResponse>, response: Response<ValidCodeResponse>) {
                        smsToken = response.body()?.token ?: ""
                        LogUtils.i("foxone", "token:: $smsToken")
                    }
                })
        }

        findViewById<Button>(R.id.btn_register).setOnClickListener {
            val name = findViewById<EditText>(R.id.edt_name).text.toString()
            val phone = findViewById<EditText>(R.id.edt_phone).text.toString()
            val smsCode = findViewById<EditText>(R.id.edt_sms_code).text.toString()
            val pwd = findViewById<EditText>(R.id.edt_password).text.toString()

            PassportAPI.register(
                RegisterReqBody(
                    nickName = name,
                    smsCode = smsCode, token = smsToken, password = pwd
                )
            )
                .enqueue(object: Callback<AccountInfo> {
                    override fun onFailure(call: Call<AccountInfo>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<AccountInfo>, response: Response<AccountInfo>) {
                        DemoApp.onLogin(
                            this@RegisterActivity,
                            response.body() ?: AccountInfo()
                        )
                        LogUtils.i("foxone", "userInfo: ${JsonUtils.optToJson(response.body())}")
                    }
                })
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }
}