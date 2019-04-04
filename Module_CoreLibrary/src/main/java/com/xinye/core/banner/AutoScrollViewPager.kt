package com.xinye.core.banner

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewParent
import android.view.animation.Interpolator
import android.widget.ListView
import android.widget.ScrollView
import com.xinye.core.banner.AutoScrollHandler.Companion.HANDLE_AUTO_SCROLL
import com.xinye.core.log.Logger

/**
 * 自动滚动ViewPager
 *
 * @author wangheng
 */
open class AutoScrollViewPager: ViewPager {
    companion object {
        private const val TAG = "Banner"
    }

    constructor(context: Context) : super(context){
        initView(context,null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        initView(context,attrs)
    }

    // 当前手指是否停留在ViewPager上
    private var isTouched = false

    // 手指按下的X轴坐标
    private var downX: Float = 0f

    // 手指按下的Y轴坐标
    private var downY: Float = 0f

    // 自动滚动的时间间隔，默认5000毫秒
    private var autoScrollInterval = 5000L

    // 是否可以自动滚动
    private var isAutoScrollable = false

    // 祖宗类可滑动组件（处理滑动冲突）
    private var mScrollableParentView: ViewParent? = null

    // 实现自动滚动的Handler
    private var mHandler = AutoScrollHandler(this@AutoScrollViewPager)

    private fun initView(context: Context, attrs: AttributeSet?) {
        setViewPagerScroller()
    }

    private fun setViewPagerScroller() {
        try {
            val scrollerField = ViewPager::class.java.getDeclaredField("mScroller")
            scrollerField.isAccessible = true
            val interpolatorField = ViewPager::class.java.getDeclaredField("sInterpolator")
            interpolatorField.isAccessible = true

            val scroller = CustomScroller(getContext(), interpolatorField.get(null) as Interpolator)
            scrollerField.set(this, scroller)
        } catch (e: Exception) {
            Logger.i(TAG,"setViewPagerScroller:$e")
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

        val action = ev.action

        if (action == MotionEvent.ACTION_DOWN) {
            isTouched = true
            stopAutoScrollInternal()
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            isTouched = false
            startAutoScrollInternal()
        }
        try {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> if (mScrollableParentView != null) {
                    downX = getSecureX(ev)
                    downY = getSecureY(ev)
                    mScrollableParentView?.requestDisallowInterceptTouchEvent(true)
                    isClickable = true
                }
                MotionEvent.ACTION_MOVE -> if (mScrollableParentView != null) {
                    val moveX = getSecureX(ev)
                    val moveY = getSecureY(ev)
                    if (mScrollableParentView is ViewPager) {
                        mScrollableParentView?.requestDisallowInterceptTouchEvent(true)
                        isClickable = true
                    } else {
                        if (Math.abs(moveX - downX) > Math.abs(moveY - downY)) {
                            mScrollableParentView?.requestDisallowInterceptTouchEvent(true)
                            isClickable = true
                        } else {
                            mScrollableParentView?.requestDisallowInterceptTouchEvent(false)
                        }
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> if (mScrollableParentView != null) {
                    mScrollableParentView?.requestDisallowInterceptTouchEvent(false)
                }
                else -> {
                }
            }
        } catch (e: Exception) {
            Logger.i(TAG,"dispatchTouchEvent:$e")
        }

        try {
            return super.dispatchTouchEvent(ev)
        } catch (e: Throwable) {
            Logger.i(TAG,"dispatchTouchEvent invoke super:$e")
        }

        return false
    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return super.onInterceptTouchEvent(ev)
        }catch (e: Throwable){
            Logger.i(TAG,"onInterceptTouchEvent:$e")
        }
        return false
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        findScrollableParentView()

        requestLayout()
        mHandler.viewPager = this
        if(adapter != null && adapter?.count ?: 0 > 1){
            startAutoScroll()
        }
    }

    override fun onDetachedFromWindow() {
        mScrollableParentView = null
        mHandler.viewPager = null
        mHandler.removeCallbacksAndMessages(null)

        super.onDetachedFromWindow()
    }

    private fun findScrollableParentView() {
        var vp: ViewParent? = parent
        while (vp != null) {
            if (vp is ViewPager
                    || vp is RecyclerView
                    || vp is ListView
                    || vp is ScrollView) {
                mScrollableParentView = vp
                return
            }
            vp = vp.parent
        }
    }

    /**
    * setAutoScrollInterval:设置切换页面的时间. <br/>
    *
    * @param autoScrollIntervalByMillis 滚动时间
    */
    fun setAutoScrollInterval(autoScrollIntervalByMillis: Long) {
        this.autoScrollInterval = autoScrollIntervalByMillis
    }

    /**
     * 开始自动滚动. <br></br>
     */
    fun startAutoScroll() {
        mHandler.viewPager = this@AutoScrollViewPager
        isAutoScrollable = true
        startAutoScrollInternal()
    }

    /**
     * 开始自动滚动，但是不会设置标记位(开始滚动之前先干掉原来的滚动信息). <br></br>
     */
    private fun startAutoScrollInternal() {
        if (isAutoScrollable) {
            stopAutoScrollInternal()
            mHandler.sendEmptyMessageDelayed(HANDLE_AUTO_SCROLL, autoScrollInterval)
        }
    }

    /**
     * 停止滚动，状态不会恢复，除非重新调用startAutoScroll()方法. <br></br>
     */
    fun stopAutoScroll() {
        mHandler.viewPager = null
        isAutoScrollable = false
        stopAutoScrollInternal()
    }

    /**
     * 停止自动滚动，但是不会设置状态，当手指离开屏幕，可以自动恢复. <br></br>
     */
    private fun stopAutoScrollInternal() {
        mHandler.removeMessages(HANDLE_AUTO_SCROLL)
    }

    /**
     * 自动滚动到下一页或者第0页. <br></br>
     */
    fun autoScroll() {
        if (adapter == null || adapter!!.count <= 1 || !isAutoScrollable) {
            return
        }

        val current = currentItem
        val count = adapter!!.count
        if (current >= 0 && current < count - 1) {
            setCurrentItem(current + 1, true)
        } else {
            setCurrentItem(0, true)
        }

        mHandler.sendEmptyMessageDelayed(AutoScrollHandler.HANDLE_AUTO_SCROLL, autoScrollInterval)
    }

    /**
     * ViewPager是否正在被触摸. <br></br>
     *
     * @return 是否正在被触摸
     */
    fun isTouched(): Boolean {
        return isTouched
    }

    /**
     * getSecureX:安全的得到MotionEvent中的x,得到失败返回0. <br></br>
     *
     * @param ev ev
     * @return x
     */
    private fun getSecureX(ev: MotionEvent): Float {
        try {
            return ev.x
        } catch (e: Throwable) {
            Logger.i(TAG,"getSecureX:$e")
        }

        return 0f
    }

    /**
     * getSecureY:安全的得到MotionEvent中的Y,得到失败返回0. <br></br>
     *
     * @param ev ev
     * @return y
     */
    private fun getSecureY(ev: MotionEvent): Float {
        try {
            return ev.y
        } catch (e: Throwable) {
            Logger.i(TAG,"getSecureY:$e")
        }

        return 0f
    }
}