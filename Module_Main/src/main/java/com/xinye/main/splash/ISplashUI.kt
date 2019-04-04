package com.xinye.main.splash

import com.xinye.architecture.mvp.IUI

/**
 * 闪屏UI
 *
 * @author wangheng
 */
interface ISplashUI: IUI {
    fun onListRequest(result: String)

}