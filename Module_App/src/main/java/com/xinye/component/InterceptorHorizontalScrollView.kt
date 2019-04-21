package com.xinye.component

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.HorizontalScrollView

class InterceptorHorizontalScrollView: HorizontalScrollView {

    constructor(context: Context?) : super(context){
        initView(context,null)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initView(context,attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        initView(context,attrs)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ){
        initView(context,attrs)
    }

    private fun initView(context: Context?, attrs: AttributeSet?) {

    }

    private var mPreviousX = 0f
    private var mPreviousY = 0f

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val action = ev?.action ?: null
        if(ev != null && action != null) {
            when(action){
                MotionEvent.ACTION_DOWN -> {
                    mPreviousX = ev.x
                    mPreviousX = ev.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val x = ev.x
                    val y = ev.y
                    if(x - mPreviousX >= y - mPreviousY){
                        requestDisallowInterceptTouchEvent(true)
                    }

                    mPreviousX = x
                    mPreviousY = y
                }
                MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL -> {
                    requestDisallowInterceptTouchEvent(false)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}