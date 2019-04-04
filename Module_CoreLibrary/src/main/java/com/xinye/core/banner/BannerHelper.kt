package com.xinye.core.banner

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.xinye.core.R
import com.xinye.core.log.Logger
import java.util.*

/**
 * Banner帮助类
 *
 * @author wangheng
 */
class BannerHelper<T : IBannerProtocol> : ViewPager.OnPageChangeListener, LifecycleCallback {
    companion object {
        private const val TAG = "Banner"

        /**
         * 开始时的延迟时间 *
         */
        private const val AUTO_SCROLL_INTERVAL = 3000L
    }

    private var mActivity: FragmentActivity? = null
    private var mFragment: Fragment? = null
    private var mViewPager: LoopViewPager? = null
    private var mIndicatorContainer: LinearLayout? = null
    private var width: Int = 0
    private var height: Int = 0
    private var mBannerList: ArrayList<T>? = null
    private var mScrollInterval = AUTO_SCROLL_INTERVAL
    private var mItemLayoutId = R.layout.layout_banner_item

    private var mCallback: IBannerCallback<T>? = null

    private val mHandler = Handler(Looper.getMainLooper())

    class Builder<B : IBannerProtocol> {
        private var width: Int = 0
        private var height: Int = 0
        private var activity: FragmentActivity? = null
        private var fragment: Fragment? = null
        private var viewPager: LoopViewPager? = null
        private var indicatorLayout: LinearLayout? = null
        private var bannerList: ArrayList<B>? = null
        private var scrollInterval: Long = AUTO_SCROLL_INTERVAL
        private var callback: IBannerCallback<B>? = null
        private var itemLayoutId = R.layout.layout_banner_item

        fun width(width: Int): Builder<B> {
            this.width = width
            return this
        }

        fun height(height: Int): Builder<B> {
            this.height = height
            return this
        }

        fun activity(activity: FragmentActivity): Builder<B> {
            this.activity = activity
            return this
        }

        fun fragment(fragment: Fragment): Builder<B> {
            this.fragment = fragment
            return this
        }

        fun viewPager(viewPager: LoopViewPager): Builder<B> {
            this.viewPager = viewPager
            return this
        }

        fun indicatorLayout(indicatorLayout: LinearLayout): Builder<B> {
            this.indicatorLayout = indicatorLayout
            return this
        }

        fun bannerList(bannerList: ArrayList<B>?): Builder<B> {
            this.bannerList = bannerList
            return this
        }

        fun scrollInterval(scrollInterval: Long): Builder<B> {
            this.scrollInterval = scrollInterval
            return this
        }

        fun callback(callback: IBannerCallback<B>?): Builder<B> {
            this.callback = callback
            return this
        }

        fun itemLayoutId(@LayoutRes id: Int): Builder<B> {
            this.itemLayoutId = id
            return this
        }

        fun build(): BannerHelper<B> {
            val helper = BannerHelper<B>()
            helper.mActivity = activity
            helper.mFragment = fragment
            helper.mBannerList = bannerList
            helper.width = width
            helper.height = height
            helper.mViewPager = viewPager
            helper.mIndicatorContainer = indicatorLayout
            helper.mCallback = callback
            helper.mScrollInterval = scrollInterval
            helper.mItemLayoutId = itemLayoutId
            return helper
        }
    }

    override fun onCreateView() {


        val viewPager = mViewPager
        if(viewPager != null){
            viewPager.addOnPageChangeListener(this@BannerHelper)
            val params = viewPager.layoutParams as FrameLayout.LayoutParams
            params.width = width
            params.height = height
           viewPager.layoutParams = params

        }

        onBannerListRequested()
    }


    override fun onDestroyView() {
        mViewPager?.removeOnPageChangeListener(this@BannerHelper)
    }

    override fun onPause() {
        stopAutoScrollIfNeed()
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    private fun stopAutoScrollIfNeed() {
        if (mViewPager != null && mBannerList != null && mBannerList?.size ?: 0 > 1) {
            mViewPager?.stopAutoScroll()
        }
    }

    private fun startAutoScrollIfNeed() {
        if (mViewPager != null && mBannerList != null && mBannerList?.size ?: 0 > 1) {
            mViewPager?.startAutoScroll()
        }
    }

    override fun onResume() {
        startAutoScrollIfNeed()
    }


    override fun onHiddenChanged(hidden: Boolean) {
        if (hidden) {
            stopAutoScrollIfNeed()
        } else {
            startAutoScrollIfNeed()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        mHandler.post { onUserVisibleHint(isVisibleToUser) }
    }

    private fun onUserVisibleHint(isVisibleToUser: Boolean) {
        if (mActivity?.isFinishing == true) {
            return
        }
        if (mFragment?.isDetached == true) {
            return
        }
        if (mViewPager != null && mBannerList != null && mBannerList?.size ?: 0 > 1) {
            if (isVisibleToUser) {
                mViewPager?.startAutoScroll()
            } else {
                mViewPager?.stopAutoScroll()
            }
        }
    }

    /**
     * onBannerListRequested:BannerList请求完成的回调 <br></br>
     *
     */
    private fun onBannerListRequested() {
        val viewPager = mViewPager ?: return

        val bannerList = mBannerList
        if (bannerList == null) {
            viewPager.visibility = View.GONE
        } else {
            if (bannerList.size == 0) {
                viewPager.visibility = View.GONE
                return
            }
            // 得到View层中的ViewPager和Indicator容器
            viewPager.visibility = View.VISIBLE

            // 得到指示器View的布局参数

            mIndicatorContainer?.removeAllViews()

            if ( bannerList.size > 1) {
                val indicatorParams = getIndicatorViewLayoutParams()

                val len = bannerList.size
                for (i in 0 until len) {
                    val banner = bannerList[i]
                    addIndicatorView(indicatorParams, i)
                }
            }

            setViewPagerAttributes()
        }
    }

    /**
     * setViewPagerAttributes:设置ViewPager的属性们. <br></br>
     */
    private fun setViewPagerAttributes() {
        val viewPager = mViewPager ?: return
        try {

            viewPager.setLoopAdapter(object : LoopViewPager.LoopPagerAdapter<T>() {
                override fun getOriginList(): List<T>? {
                    return mBannerList
                }

                override fun createView(banner: T, position: Int): View {
//                    val view = ImageView(mActivity)
//                    val bannerParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//                    view.layoutParams = bannerParams
//                    view.scaleType = ImageView.ScaleType.CENTER_CROP
                    val inflater = mActivity?.layoutInflater
                    val view = inflater?.inflate(mItemLayoutId,viewPager,false)
                    view?.setOnClickListener {
                        val callback = mCallback
                        val list = mBannerList
                        if (callback != null && list != null) {
                            callback.onBannerItemClick(view, banner, position)
                        }
                    }
                    return view!!
                }

                override fun bindDataToView(view: View, banner: T, position: Int) {
                    val imageView: ImageView = view.findViewById(R.id.bannerImageItem)
                    val activity = mActivity
                    if (activity != null) {
                        Glide.with(activity)
                                .load(banner.getBannerImageUrl())
//                                .placeholder(R.drawable.banner_loading)
//                                .error(R.drawable.banner_loading)
                                .into(imageView)
                    }
                }
            })

            val list = mBannerList
            if (list != null && list.size > 1) {
                viewPager.offscreenPageLimit = list.size
                viewPager.setCurrentItem(1, false)
                viewPager.setAutoScrollInterval(mScrollInterval)
                viewPager.startAutoScroll()
            } else if (list != null && list.size == 1) {
                viewPager.currentItem = 0
            }
        } catch (e: Exception) {
            Logger.e(TAG,"setViewPagerAttributes:$e")
        }

    }

    /**
     * getIndicatorViewLayoutParams:得到指示器的布局参数. <br></br>
     *
     * @return LayoutParams
     */
    private fun getIndicatorViewLayoutParams(): LinearLayout.LayoutParams {
        val indicatorParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        indicatorParams.gravity = Gravity.CENTER
        val activity = mActivity
        if(activity != null){
            indicatorParams.leftMargin = dp2px(activity, 2f)
            indicatorParams.rightMargin = dp2px(activity,2f)
        }else{
            indicatorParams.leftMargin = 6
            indicatorParams.rightMargin = 6
        }
        return indicatorParams
    }

    private fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * addIndicatorView:添加指示器View. <br></br>
     *
     * @param indicatorParams params
     * @param position position
     */
    private fun addIndicatorView(indicatorParams: LinearLayout.LayoutParams, position: Int) {
        val indicatorView = ImageView(mActivity)

        indicatorView.setOnClickListener {
                mViewPager?.currentItem = position
        }

        indicatorView.layoutParams = indicatorParams
        indicatorView.scaleType = ImageView.ScaleType.FIT_XY
        if (0 == position) {
            indicatorView.setImageResource(R.drawable.circle_banner_checked)
        } else {
            indicatorView.setImageResource(R.drawable.circle_banner_normal)
        }
        mIndicatorContainer?.addView(indicatorView)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        val indicatorLayout = mIndicatorContainer
        if(indicatorLayout != null) {
            val count = indicatorLayout.childCount
            for (i in 0 until count) {
                val indicatorView = indicatorLayout.getChildAt(i) as ImageView
                if (i == position) {
                    indicatorView.setImageResource(R.drawable.circle_banner_checked)
                } else {
                    indicatorView.setImageResource(R.drawable.circle_banner_normal)
                }
            }
        }

        val callback = mCallback
        val list = mBannerList
        if (callback != null && list != null) {
            callback.onBannerSelected(list[position], position)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}