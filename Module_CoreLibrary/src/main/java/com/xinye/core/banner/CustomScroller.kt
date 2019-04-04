package com.xinye.core.banner

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller

/**
 * 用于重新设置ViewPager滚动速度的Scroller
 *
 * @author wangheng
 */
class CustomScroller: Scroller {
    companion object {
        private const val DURATION_DEFAULT = 750
    }

    private var mDuration: Int = DURATION_DEFAULT

    constructor(context: Context,duration: Int): super(context){
        this.mDuration = duration
    }

    constructor(context: Context,interpolator: Interpolator): super(context){
        this.mDuration = DURATION_DEFAULT
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        this.startScroll(startX,startY,dx,dy,mDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }
}