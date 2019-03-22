package com.xinye.core

import android.content.Context
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
}