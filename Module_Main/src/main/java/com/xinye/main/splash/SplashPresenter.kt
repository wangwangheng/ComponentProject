package com.xinye.main.splash

import com.xinye.architecture.mvp.BasePresenter
import com.xinye.core.log.Logger

/**
 * 闪屏
 *
 * @author wangheng
 */
class SplashPresenter: BasePresenter<ISplashUI>() {
    fun requestList() {
        Logger.i("wangheng","SplashPresenter#requestList()")
        ui.onListRequest("hehhehe")
    }
}