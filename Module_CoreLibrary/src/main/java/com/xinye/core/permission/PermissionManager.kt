package com.xinye.core.permission

import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.xinye.core.App
import com.xinye.core.log.Logger
import java.util.concurrent.Executors

/**
 * 权限管理器
 *
 * @author wangheng
 */
object PermissionManager {

    private const val TAG = "PermissionListChecker"

    private fun isSdkUp23(): Boolean {
        return Build.VERSION.SDK_INT >= 23
    }

    private fun hasPermission(permission: String): Boolean {
        var hasPermission = true
        //6.0或者以后
        if (isSdkUp23()) {
            hasPermission = PermissionChecker.checkSelfPermission(App.getContext(), permission) == PackageManager.PERMISSION_GRANTED
            Logger.i(TAG, ">=23 hasPermission = $hasPermission")
        }
        return hasPermission
    }

    fun checkAllPermission(activity: AppCompatActivity,
                           requestList: ArrayList<String> ,
                           body: (deniedList: ArrayList<String>,isAll: Boolean) -> Unit = {list:ArrayList<String>,isAll: Boolean->}) {

        Executors.newSingleThreadExecutor().execute {
            val deniedList = ArrayList<String>()
            var isAllPermission = true
            if (isSdkUp23()) {
                requestList.forEach {
                    if(!hasPermission(it)){
                        isAllPermission = true
                        deniedList.add(it)
                    }
                }
            }

            activity.runOnUiThread {
                body(deniedList,isAllPermission)
            }
        }
    }

    private fun isNotAskChecked(activity: AppCompatActivity,permission: String): Boolean{
        return !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    fun getDeniedPermissionList(activity: AppCompatActivity,requestList: ArrayList<String>): ArrayList<String>{
        val deniedList = ArrayList<String>()

        if (isSdkUp23()) {
            requestList.forEach {
                if(!hasPermission(it)){
                    deniedList.add(it)
                }
            }
        }

        return deniedList
    }

    /**
     * 权限请求
     * @param body 第一个参数:true-用户点击了不在询问并拒绝,第二个参数:true-全部允许, false-有权限拒绝
     */
    fun requestPermission(activity: AppCompatActivity,requestList: ArrayList<String> ,body: (selectCheckbox: Boolean, isAll: Boolean) -> Unit) {
        if (isSdkUp23()) {
            val array:Array<String> = requestList.toTypedArray()
            val subscribe = RxPermissions(activity).requestEachCombined(*array)
                .subscribe { permission ->

                    var isNotAskChecked = false
                    if (!permission.granted) {
                        for (p in requestList) {
                            if (isNotAskChecked(activity, p)) {
                                isNotAskChecked = true
                                break
                            }
                        }
                    }

                    body(isNotAskChecked, permission.granted)
                }
        } else {
            body(false, true)
        }
    }
}