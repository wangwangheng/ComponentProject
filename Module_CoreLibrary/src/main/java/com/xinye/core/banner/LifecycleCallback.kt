package com.xinye.core.banner

/**
 * 生命周期回调
 *
 * @author wangheng
 */
interface LifecycleCallback {
    fun onCreateView()

    fun onDestroyView()

    fun onResume()

    fun onPause()

    fun onStart()

    fun onStop()

    fun onHiddenChanged(hidden: Boolean)

    fun setUserVisibleHint(isVisibleToUser: Boolean)
}