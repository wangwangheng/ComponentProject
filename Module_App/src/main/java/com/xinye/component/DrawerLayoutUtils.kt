package com.xinye.component

import android.app.Activity
import android.graphics.Point
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.ViewDragHelper
import android.view.View
import com.xinye.core.log.Logger
import java.lang.Exception

/**
 * DrawerLayout工具类
 *
 * @author wangheng
 */
object DrawerLayoutUtils {

    fun setLeftEdgeSize(activity: Activity?,drawerLayout: DrawerLayout?,widthPercent: Float) {
        if (activity == null || drawerLayout == null) {
            return;
        }
        try{

            val leftDraggerField = drawerLayout::class.java.getDeclaredField("mLeftDragger")
            leftDraggerField.isAccessible = true
            val leftDragger = leftDraggerField.get(drawerLayout) as ViewDragHelper

            val edgeSizeField = leftDragger::class.java.getDeclaredField("mEdgeSize")
            edgeSizeField.isAccessible =  true
            val edgeSize = edgeSizeField.get(leftDragger) as Int


            val displaySize = Point()
            activity.windowManager.defaultDisplay.getSize(displaySize)
            val size = Math.max(edgeSize,(displaySize.x * widthPercent).toInt())
            edgeSizeField.setInt(leftDragger,size)
        }catch (e: Exception){
            Logger.i("DrawerLayoutUtils","DrawerLayoutUtils#setLeftEdgeSize error:$e")
        }
    }

    fun setDrawerWidth(activity: Activity?,drawerLayout: View?,widthPercent: Float){
        if (activity == null || drawerLayout == null) {
            return;
        }

        val params = drawerLayout.layoutParams
        val displaySize = Point()
        activity.windowManager.defaultDisplay.getSize(displaySize)
        params.width = (displaySize.x * widthPercent).toInt()
        drawerLayout.layoutParams = params
    }

}