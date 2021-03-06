package com.fox.one.demo

import android.accounts.Account
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.fox.one.passport.core.PassportAPI
import com.fox.one.passport.core.model.AccountInfo
import com.fox.one.passport.core.model.LoginWithPhoneReqBody
import com.fox.one.support.common.utils.JsonUtils
import com.fox.one.support.common.utils.LogUtils
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
class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            val phone = findViewById<EditText>(R.id.edt_phone).text.toString()
            val pwd = findViewById<EditText>(R.id.edt_password).text.toString()

            PassportAPI.login(LoginWithPhoneReqBody(countryCode = "86", phoneNumber = phone, password = pwd))
                .enqueue(object: Callback<AccountInfo> {
                override fun onFailure(call: Call<AccountInfo>, t: Throwable) {
                    LogUtils.i("foxone", "${t.toString()}")
                    HttpErrorHandler.handle(t)
                }

                override fun onResponse(call: Call<AccountInfo>, response: Response<AccountInfo>) {
                    AccountManager.login(response.body() ?: AccountInfo())
                    LogUtils.i("foxone", "login::${JsonUtils.optToJson(response.body())}")
                    finish()
                }
            })
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }
}