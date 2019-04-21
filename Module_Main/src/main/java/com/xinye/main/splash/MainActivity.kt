package com.xinye.main.splash

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.xinye.architecture.base.BaseActivity
import com.xinye.core.log.Logger

@Route(path = "/main/main")
class MainActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = intent.getStringExtra("name")
        val extras = intent.extras
        Logger.i("wangheng","MainActivity::$name,${extras["name"]}")
    }
}