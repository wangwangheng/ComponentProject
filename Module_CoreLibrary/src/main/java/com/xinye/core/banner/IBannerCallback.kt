package com.xinye.core.banner

import android.view.View

/**
 * Banner回调
 *
 * @author wangheng
 */
interface IBannerCallback<T: IBannerProtocol> {
    /**
     * onBannerItemClick的Item被点击的时候的回调. <br></br>
     *
     * @param itemView itemView
     * @param item item
     * @param position position
     */
    fun onBannerItemClick(itemView: View, item: T, position: Int)

    fun onBannerSelected(item: T, pageIndex: Int)
}