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
        
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }
}