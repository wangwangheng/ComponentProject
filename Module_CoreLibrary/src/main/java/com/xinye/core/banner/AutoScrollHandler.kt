package com.xinye.core.banner

import android.os.Handler
import android.os.Message

/**
 * 自动滚动的Handler
 *
 * @author wangheng
 */
class AutoScrollHandler(var viewPager: AutoScrollViewPager?): Handler() {

    companion object {
        // 自动滚动需要发送的消息的what
        const val HANDLE_AUTO_SCROLL = 0x1001
    }

    override fun handleMessage(msg: Message?) {
        when (msg?.what) {
            HANDLE_AUTO_SCROLL ->  {
                viewPager?.autoScroll()
            }

            else -> {
            }
        }
    }

}