package com.fox.one.support

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.fox.one.support.common.utils.JsonUtils
import com.fox.one.support.common.utils.LogUtils
import com.fox.one.support.passport.LoginActivity
import com.fox.one.support.passport.RegisterActivity
import com.fox.one.support.passport.SMSLoginActivity
import com.fox.one.support.passport.core.models.AccountInfo
import com.fox.one.support.passport.core.PassportAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            PassportAPI.getAccountInfo().enqueue(object: Callback<AccountInfo> {
                override fun onFailure(call: Call<AccountInfo>, t: Throwable) {
                    if (t is HttpException) {
                        LogUtils.i("foxone", "${t.response().errorBody()?.string()}")
                    } else {
                        LogUtils.i("foxone", "${t.toString()}")
                    }
                }

                override fun onResponse(call: Call<AccountInfo>, response: Response<AccountInfo>) {
                    LogUtils.i("foxone", "${JsonUtils.optToJson(response.body())}")
                }

            })
        }
    }
}
