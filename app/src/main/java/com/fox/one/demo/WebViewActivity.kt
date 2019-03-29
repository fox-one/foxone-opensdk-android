package com.fox.one.demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.JavascriptInterface
import android.webkit.WebView

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-23
 */
class WebViewActivity: AppCompatActivity() {

    private val webview by lazy { findViewById<WebView>(R.id.webView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_webview)

        webview.loadUrl("file:///android_asset/index.html")

        val settings = webview.settings
        settings.javaScriptEnabled = true
        WebView.setWebContentsDebuggingEnabled(true)
        settings.javaScriptCanOpenWindowsAutomatically = true
        webview.addJavascriptInterface(JSInterface(), "WebViewJavascriptBridge")
    }

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, WebViewActivity::class.java))
        }
    }
}

class JSInterface {

    @JavascriptInterface
    fun hello(callback: JSCallback) {

        callback.onCallback(">>>>>>>>>>>>>>>")
    }
}

interface JSCallback {
    fun onCallback(content: String)
}