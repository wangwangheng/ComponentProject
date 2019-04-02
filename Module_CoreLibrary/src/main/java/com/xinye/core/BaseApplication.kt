package com.xinye.core

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDexApplication

/**
 * 所有业务组件的Application都要继承于这个Application; 但是，除了直接子类之外，其他类不应该跟BaseApplication有直接交集
 *
 * @author wangheng
 */
abstract class BaseApplication: MultiDexApplication() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        App.init(this,BuildConfig.DEBUG)
    }

    override fun onCreate() {
        super.onCreate()
        AppLifecycleManager.getInstance().init(this)
        AppLifecycleManager.getInstance().onCreate(applicationContext)
//
//        registerActivityLifecycleCallbacks(object: ActivityLifecycleCallbacks {
//            override fun onActivityPaused(activity: Activity?) {
//            }
//
//            override fun onActivityResumed(activity: Activity?) {
//            }
//
//            override fun onActivityStarted(activity: Activity?) {
//            }
//
//            override fun onActivityDestroyed(activity: Activity?) {
//            }
//
//            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
//            }
//
//            override fun onActivityStopped(activity: Activity?) {
//            }
//
//            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
//            }
//
//        })
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTerminate() {
        AppLifecycleManager.getInstance().onTerminate()
        super.onTerminate()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }
}