package com.xinye.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity_splash.*

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_splash)

        setSupportActionBar(splash_toolbar)
        splash_toolbar.setNavigationOnClickListener {

        }

    }
}