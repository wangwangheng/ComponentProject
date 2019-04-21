package com.xinye.component

import android.app.Activity
import android.os.Bundle
import com.xinye.core.toast.ToastUtils
import kotlinx.android.synthetic.main.app_activity_main.*

class MainActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.app_activity_main)


        top_view_main.setLeftImageClickListener { ToastUtils.toastShort("左边图片") }
        top_view_main.setRightTextClickListener { ToastUtils.toastShort("右边文字") }
        top_view_main.setTitleClickListener { ToastUtils.toastShort("标题文字")  }
        top_view_main.setRightImageClickListener { ToastUtils.toastShort("右边图片")  }

        top_view_main.setLeftImageResource(R.drawable.abc_ic_ab_back_material)
        top_view_main.setRightImageResource(R.drawable.abc_ic_search_api_material)
    }
}