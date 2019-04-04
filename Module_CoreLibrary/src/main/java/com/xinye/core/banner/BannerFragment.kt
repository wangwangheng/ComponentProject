package com.xinye.core.banner

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import com.xinye.core.App
import com.xinye.core.R
import java.util.*

/**
 * 调用Banner模块的时候需要给Banner模块设置宽度、高度、数据列表和Banner点击的回调
 *
 * (不要在RecyclerView或者ListView这种可重用Item的View中使用，否则可能Crash；这种场景请使用BannerHelper手动设置)
 *
 *
 * @author wangheng
 */
class BannerFragment<T : IBannerProtocol> : Fragment() {

    companion object {

        private const val TAG = "Banner"

        const val KEY_WIDTH = "keyWidth"
        const val KEY_HEIGHT = "keyHeight"
        const val KEY_BANNER_LIST = "keyBannerList"
        const val KEY_BANNER_ITEM_LAYOUT = "keyBannerItemLayout"

        // 开始时的延迟时间
        private const val AUTO_SCROLL_INTERVAL = 3000L

        private const val RATIO_DEFAULT = 0.5f

        fun <Banner : IBannerProtocol> create(bannerList: ArrayList<Banner>): BannerFragment<Banner> {

            val width = getScreenWidth()
            val height = (width * RATIO_DEFAULT).toInt()

            return create(bannerList, width, height)
        }

        fun <Banner : IBannerProtocol> create(bannerList: ArrayList<Banner>,
                                              width: Int, height: Int): BannerFragment<Banner> {
            return create(bannerList,width,height, R.layout.layout_banner_item)
        }


        fun <Banner : IBannerProtocol> create(bannerList: ArrayList<Banner>,
                                              width: Int, height: Int,
                                              @LayoutRes itemLayoutId: Int): BannerFragment<Banner> {
            val bundle = Bundle()
            bundle.putInt(KEY_WIDTH, width)
            bundle.putInt(KEY_HEIGHT, height)
            bundle.putSerializable(KEY_BANNER_LIST, bannerList)
            bundle.putInt(KEY_BANNER_ITEM_LAYOUT,itemLayoutId)

            val fragment = BannerFragment<Banner>()
            fragment.arguments = bundle
            return fragment
        }



        private fun getScreenWidth(): Int {
            val wm = App.getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val point = Point()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                wm.defaultDisplay.getRealSize(point)
            } else {
                wm.defaultDisplay.getSize(point)
            }
            return point.x
        }
    }

    /**
     * banner 列表 *
     */
    private var mBannerList: ArrayList<T>? = null

    private var mRootView: View? = null


    private var mCallback: IBannerCallback<T>? = null

    private var mHelper: BannerHelper<T>? = null

    /**
     * setCallback:设置Banner的事件Callback. <br></br>
     *
     * @param callback mCallback
     */
    fun setCallback(callback: IBannerCallback<T>) {
        this.mCallback = callback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var width: Int
        var height: Int
        val itemLayoutId: Int
        val data = arguments
        if (data != null) {
            width = data.getInt(KEY_WIDTH)
            height = data.getInt(KEY_HEIGHT)
            itemLayoutId = data.getInt(KEY_BANNER_ITEM_LAYOUT)
            if (width == 0 || height == 0) {
                width = getScreenWidth()
                height = (width * RATIO_DEFAULT).toInt()
            }
            mBannerList = data.getSerializable(KEY_BANNER_LIST) as ArrayList<T>
        } else {
            width = getScreenWidth()
            height = (width * RATIO_DEFAULT).toInt()
            itemLayoutId = R.layout.layout_banner_item
        }

        if (mRootView == null) {
            val rootView = inflater.inflate(R.layout.fragment_banner, container, false)
            if(rootView != null) {
                val bannerIndicatorContainer = rootView.findViewById<LinearLayout>(R.id.bannerIndicatorContainer)
                val bannerViewPager = rootView.findViewById<LoopViewPager>(R.id.bannerViewPager)
                if(bannerViewPager != null && bannerIndicatorContainer != null) {
                    mHelper = BannerHelper.Builder<T>()
                            .activity(activity!!)
                            .fragment(this)
                            .bannerList(mBannerList)
                            .callback(mCallback)
                            .width(width)
                            .height(height)
                            .indicatorLayout(bannerIndicatorContainer)
                            .viewPager(bannerViewPager)
                            .scrollInterval(AUTO_SCROLL_INTERVAL)
                            .itemLayoutId(itemLayoutId)
                            .build()
                    mHelper?.onCreateView()
                }
            }
            mRootView = rootView
        }
        return mRootView
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mHelper?.onDestroyView()

        val parent = mRootView?.parent

        if (parent != null && parent is ViewGroup) {
            parent.removeView(mRootView)
        }
    }

    override fun onPause() {
        mHelper?.onPause()
        super.onPause()
    }


    override fun onResume() {
        super.onResume()
        mHelper?.onResume()
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mHelper?.onHiddenChanged(hidden)
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mHelper?.setUserVisibleHint(isVisibleToUser)
    }

    override fun onDestroy() {
        mHelper?.onDestroyView()
        super.onDestroy()
    }


}