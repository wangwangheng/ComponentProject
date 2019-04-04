package com.xinye.component

import android.content.Context
import com.xinye.core.BaseApplication

/**
 * 真正的Application对象
 *
 * @author wangheng
 */
class AppApplication: BaseApplication() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}