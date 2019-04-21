package com.xinye.component

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.xinye.core.toast.ToastUtils
import kotlinx.android.synthetic.main.app_activity_main.*
import kotlinx.android.synthetic.main.app_view_activity_main.*

/**
 * 首页
 *
 * @author wangheng
 */
class MainActivity: Activity(), DrawerLayout.DrawerListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_main)


        top_view_main.setLeftImageClickListener {
            drawer_layout.openDrawer(Gravity.START)
        }
        top_view_main.setRightTextClickListener { ToastUtils.toastShort("右边文字") }
        top_view_main.setTitleClickListener { ToastUtils.toastShort("标题文字")  }
        top_view_main.setRightImageClickListener { ToastUtils.toastShort("右边图片")  }

        top_view_main.setLeftImageResource(R.drawable.abc_ic_ab_back_material)
        top_view_main.setRightImageResource(R.drawable.abc_ic_search_api_material)

        initDrawer()

    }

    private fun initDrawer() {
        DrawerLayoutUtils.setLeftEdgeSize(this,drawer_layout,0.64f)
        DrawerLayoutUtils.setDrawerWidth(this,ll_drawer,0.64f)
        drawer_layout?.addDrawerListener(this)

        // 我的钱包
        icv_my_wallet.setOnClickListener {
        }

        // 我的行程
        icv_my_order.setOnClickListener {
        }

        // 开票管理
        icv_invoice_manager.setOnClickListener {
        }

        // 附加费订单
        icv_additional_cost.setOnClickListener {
        }

        // 违章记录
        icv_violation_regulations.setOnClickListener {
        }

        // 设置
        icv_settings.setOnClickListener {
        }

        // 意见反馈
        icv_feedback.setOnClickListener {
        }
    }

    override fun onDestroy() {
        drawer_layout?.removeDrawerListener(this)
        super.onDestroy()
    }

    override fun onDrawerStateChanged(p0: Int) {
    }

    override fun onDrawerSlide(p0: View, p1: Float) {
    }

    override fun onDrawerClosed(p0: View) {
    }

    override fun onDrawerOpened(p0: View) {
    }
}