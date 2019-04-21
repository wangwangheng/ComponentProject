package com.xinye.main.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.xinye.architecture.mvp.BaseMVPActivity
import com.xinye.architecture.mvp.IUI
import com.xinye.core.log.Logger
import com.xinye.main.R
import kotlinx.android.synthetic.main.main_activity_splash.*

/**
 * 闪屏页
 *
 * @author wangheng
 */
class SplashActivity: BaseMVPActivity<SplashPresenter>() , ISplashUI {


    override fun onCreateExecute(savedInstanceState: Bundle?) {
        setContentView(R.layout.main_activity_splash)

        setSupportActionBar(splash_toolbar)
        splash_toolbar.setNavigationOnClickListener {
            finish()
        }

        mPresenter?.requestList()

        Glide.with(this)
            .load("https://bbswater-fd.zol-img.com.cn/t_s1200x5000/g5/M00/0E/08/ChMkJlrdXhSIIM9yAAzKKag-ytAAAn0iABn5ZgADMpB324.jpg")
            .into(splash_image)

        splash_image.setOnClickListener {
            // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
            ARouter.getInstance()
                .build("/main/main")
                .withString("name","张三")
                .navigation()
        }
    }

    override fun onListRequest(result: String) {
        Logger.i("wangheng","数据请求成功：$result")
    }
}