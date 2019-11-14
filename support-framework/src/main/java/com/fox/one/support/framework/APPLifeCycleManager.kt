package com.fox.one.support.framework

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.fox.one.support.common.utils.LogUtils
import java.util.concurrent.CopyOnWriteArrayList

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-08-31
 */
object APPLifeCycleManager {

    private const val TAG = "APPLifecycleManager"
    private var activeCount = 0
    private var mInForeGround = false
    private var mActivityList = CopyOnWriteArrayList<Activity>()
    private var mAppLifecycleCallback = CopyOnWriteArrayList<AppLifecycleCallback>()

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                addActivityToStack(activity)

                if (!isInForeground()) {
                    willGoToForeGround()
                }
            }

            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
                activeCount++
                if (!isInForeground()) {
                    willGoToForeGround()
                }
            }

            override fun onActivityDestroyed(activity: Activity?) {
                removeActivityFromStack(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
                activeCount--
                if (isInForeground() && activeCount == 0) {
                    willGoToBackGround()
                }
            }
        })
    }


    private fun addActivityToStack(activity: Activity) {
        val iterator = this.mActivityList.iterator()
        var itemWeakActivity: Activity
        do {
            if (!iterator.hasNext()) {
                this.mActivityList.add(activity)
                return
            }
            itemWeakActivity = iterator.next()
        } while (itemWeakActivity !== activity)
    }

    private fun removeActivityFromStack(activity: Activity?) {
        val iterator = this.mActivityList.iterator()
        var itemWeakActivity: Activity
        do {
            if (!iterator.hasNext()) {
                return
            }

            itemWeakActivity = iterator.next()
        } while (itemWeakActivity !== activity)

        this.mActivityList.remove(itemWeakActivity)
    }

    private fun willGoToBackGround() {
        mInForeGround = false
        mAppLifecycleCallback.forEach { it.onAppGoToBackGround() }
    }

    private fun willGoToForeGround() {
        mInForeGround = true
        mAppLifecycleCallback.forEach { it.onAppGoToForeGround() }
    }

    fun registAppLifecycleCallback(callback: AppLifecycleCallback) {
        mAppLifecycleCallback.add(callback)
    }

    fun unRegistAppLifecycleCallback(callback: AppLifecycleCallback) {
        mAppLifecycleCallback.remove(callback)
    }

    fun isInForeground(): Boolean = mInForeGround

    /**
     * 判断是否存在Activity没有被销毁，即没有回调`onDestroy()`方法
     */
    fun hasActivityAlive(): Boolean {
        return !mActivityList.isEmpty()
    }

    fun getActivityStack(): List<Activity?> = mActivityList

    fun getCurrentActivity(): Activity? {
        val currentActivity = if (mActivityList.size > 0) mActivityList.last() else null
        if (currentActivity != null && currentActivity.isFinishing) {
            this.removeActivityFromStack(currentActivity)
            return getCurrentActivity()
        } else {
            return currentActivity
        }
    }

    fun <T : Activity> clearActivitiesExcept(clazz: Class<T>) {
        for (ref in getActivityStack()) {
            if (ref != null && ref::class.java != clazz) {
                ref.finish()
            }
        }
    }

    fun clearActivitiesExcept(activity: Activity?) {
        for (ref in getActivityStack()) {
            if (ref != null && ref != activity) {
                ref.finish()
            }
        }
    }
}