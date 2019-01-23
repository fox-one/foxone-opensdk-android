package com.fox.one.support.common.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.widget.Toast

/**
 * Created by zhangyinghao on 2018/4/9.
 */
object ToastUtil {
    private var mToast: Toast? = null
    private val handler = Handler(Looper.getMainLooper())

    fun show(context: Context, resId: Int) {
        show(context, context.getString(resId))
    }

    fun show(context: Context?, text: CharSequence) {
        if (context == null || TextUtils.isEmpty(text)) {
            return
        }

        if (Looper.myLooper() != Looper.getMainLooper()) {
            handler.post {
                updateOrCreateToast(context, text)
            }
        } else {
            updateOrCreateToast(context, text)
        }
    }

    private fun updateOrCreateToast(context: Context?, text: CharSequence) {

        if (mToast != null) {
            mToast!!.setText(text)
        } else {
            mToast = Toast.makeText(context!!.applicationContext, text, Toast.LENGTH_LONG)
        }

        mToast!!.show()
    }
}
