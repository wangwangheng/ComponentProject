package com.xinye.main.splash

import android.content.Context
import com.xinye.core.IAppLifecycle
import com.xinye.core.IAppLifecycle.MIN_PRIORITY
import com.xinye.core.IAppLifecycle.TAG
import com.xinye.core.log.Logger
import com.xinye.lib_annotation.AppLifecycle

@AppLifecycle
class MainInit: IAppLifecycle {
    override fun getPriority(): Int {
        return MIN_PRIORITY
    }

    override fun onCreate(context: Context?) {
        Logger.i(TAG,"MainInit#onCreate()")
    }

    override fun onTerminate() {
        Logger.i(TAG,"MainInit#onTerminate()")
    }
}