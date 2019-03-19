package com.xinye.core

import android.content.Context

/**
 * App,所有组件除了Application应该不跟BaseApplication打交道，而是应该通过此单例类获取应用资源
 *
 * @author wangheng
 */
class App private constructor(){
    companion object {
        private val INSTANCE = App()
        fun getInstance(): App {
            return INSTANCE
        }
    }

    private lateinit var mContext: Context

    fun init(context: Context){
        mContext = context
    }

    fun getContext(): Context {
        return mContext
    }


}