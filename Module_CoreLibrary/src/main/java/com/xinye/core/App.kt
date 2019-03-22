package com.xinye.core

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.support.annotation.StringRes

/**
 * App,所有组件除了Application应该不跟BaseApplication打交道，而是应该通过此单例类获取应用资源
 *
 * @author wangheng
 */
@SuppressWarnings(" StaticFieldLeak")
object App{

    private lateinit var mContext: Application
    private var mDebug = true

    fun init(context: Application,debug: Boolean){
        mContext = context
        mDebug = debug
    }

    fun getContext(): Context {
        return mContext
    }

    fun isDebug(): Boolean {
        return mDebug
    }

    fun getString(@StringRes id: Int): String {
        return mContext.getString(id)
    }

    fun getResources(): Resources {
        return mContext.resources
    }

}