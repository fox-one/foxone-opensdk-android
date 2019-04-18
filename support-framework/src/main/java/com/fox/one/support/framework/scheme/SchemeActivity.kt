package com.fox.one.support.framework.scheme

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fox.one.support.framework.APPLifeCycleManager
import com.fox.one.support.framework.FoxRuntime

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-08-21
 */
class SchemeActivity:AppCompatActivity() {

    private var fromPush = false

    companion object {
        const val KEY_FROM_PUSH  = "fromPush"
        const val DELAY = 600L

        fun start(context: Context, uri: Uri?) {
            FoxRuntime.handler.postDelayed({
                start(context, false, uri)
            }, DELAY)
        }

        fun startFromPush(context: Context, uri: Uri?) {
            FoxRuntime.handler.postDelayed({
                start(context, true, uri)
            }, DELAY)
        }

        fun start(context: Context, fromPush: Boolean, uri: Uri?) {
            FoxRuntime.handler.postDelayed({
                val intent = Intent(context, SchemeActivity::class.java)

                intent.data = uri

                intent.putExtra(KEY_FROM_PUSH, fromPush)

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                context.startActivity(intent)
            }, DELAY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        intent?.also {
            fromPush = it.getBooleanExtra(KEY_FROM_PUSH, false)

            SchemeRoute.activeScheme = it.data
            if (fromPush) {
                if (APPLifeCycleManager.hasActivityAlive()) {
                    //handle scheme
                    handleScheme(this, SchemeRoute.activeScheme)
                    SchemeRoute.activeScheme = null
                } else {
                    //do nothing
                }
            } else {
                if (APPLifeCycleManager.hasActivityAlive()) {
                    //handle scheme
                    handleScheme(this, SchemeRoute.activeScheme)
                    SchemeRoute.activeScheme = null
                } else {
                    //wakeup app
                    SchemeRoute.wakeupAPP()
                }
            }
        }

        finish()
    }

    private fun handleScheme(context: Context, uri: Uri?) {
        SchemeRoute.handleScheme(context, uri)
    }
}