package com.xinye.core.banner

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.xinye.core.log.Logger
import java.util.*

/**
 * 可以循环滚动的ViewPager
 *
 * @author wangheng
 */
class LoopViewPager: AutoScrollViewPager {
    companion object {
        private const val TAG = "Banner"

    }

    constructor(context: Context, attrs: AttributeSet?): super(context,attrs) {
        initView(context, attrs)
    }

    constructor(context: Context): super(context) {
        initView(context, null)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        super.addOnPageChangeListener(mOnPagerChangeListener)

    }

    override fun onDetachedFromWindow() {
        super.removeOnPageChangeListener(mOnPagerChangeListener)
        super.onDetachedFromWindow()
    }

    private val mListener = ArrayList<OnPageChangeListener>()

    override fun addOnPageChangeListener(listener: OnPageChangeListener) {
        //        super.addOnPageChangeListener(listener);
        mListener.add(listener)
    }

    override fun removeOnPageChangeListener(listener: OnPageChangeListener) {
        //        super.removeOnPageChangeListener(listener);
        mListener.remove(listener)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {

    }

    override fun setAdapter(adapter: PagerAdapter?) {
        //        super.setAdapter(adapter);
        throw IllegalArgumentException("this method nothing to do,please use setLoopAdapter replace")
    }

    fun setLoopAdapter(adapter: LoopPagerAdapter<*>) {
        super.setAdapter(Adapter(adapter))
    }

    // 内部的OnBannerPageChangeListener：为了实现外部onPageChange的回调和Banner的无限循环
    private val mOnPagerChangeListener = object : OnPageChangeListener {

        private var previousIndex = 0

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            val adapter = adapter ?: return
            val count = adapter.count
            if (count <= 1) {
                for (listener in mListener) {
                    listener.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }
                return
            }
            val realIndex: Int
            try {
                if (position == count - 1 && positionOffset == 0f) {
                    realIndex = 1
                    setCurrentItem(realIndex, false)
                } else if (position == 0 && positionOffset == 0f) {
                    realIndex = count - 2
                    setCurrentItem(realIndex, false)
                } else {
                    realIndex = position
                }

                for (listener in mListener) {
                    listener.onPageScrolled(realIndex, positionOffset, positionOffsetPixels)
                }
            } catch (e: Exception) {
                Logger.e(TAG,"onPageScrolled:$e")
            }

        }

        override fun onPageSelected(position: Int) {
            val adapter = adapter ?: return
            val count = adapter.count
            if (count <= 1) {
                for (listener in mListener) {
                    listener.onPageSelected(position)
                }
                return
            }
            val originCount = count - 2
            val realIndex: Int

            if (position == 0) {
                realIndex = originCount - 1
            } else if (position == count - 1) {
                realIndex = 0
            } else {
                realIndex = position - 1
            }

            if (previousIndex != realIndex) {
                for (listener in mListener) {
                    listener.onPageSelected(realIndex)
                }
            }
            previousIndex = realIndex
        }

        override fun onPageScrollStateChanged(state: Int) {
            for (listener in mListener) {
                listener.onPageScrollStateChanged(state)
            }
        }
    }

    /**
     * Adapter
     *
     * @author wangheng
     */
    private inner class Adapter<T : IBannerProtocol>(private val mAdapter: LoopPagerAdapter<T>) : PagerAdapter() {

        private var mBannerList: List<T>? = null

        init {
            this.mBannerList = mAdapter.createWrapperList()
        }

        override fun getCount(): Int {
            return mBannerList?.size ?: 0
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val bannerList = mBannerList
            if (bannerList != null && bannerList.isNotEmpty()) {
                val pageIndex: Int
                val len = bannerList.size
                if (position == 0) {
                    pageIndex = len - 1
                } else if (position == len - 1) {
                    pageIndex = 0
                } else {
                    pageIndex = position - 1
                }
                try {
                    val view = mAdapter.createView(bannerList[position], pageIndex)
                    container.addView(view, 0)
                    mAdapter.bindDataToView(view, bannerList[position], pageIndex)
                    return view
                } catch (e: Exception) {
                    Logger.e(TAG,"instantiateItem:$e")
                }
            }
            return ""
        }


        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            if(obj is View) {
                container.removeView(obj)
            }
        }
    }

    /**
     * 外部提供数据列表，ItemView的创建和数据绑定
     *
     * @author wangheng
     */
    abstract class LoopPagerAdapter<T : IBannerProtocol> {

        private val mList = ArrayList<T>()

        fun clearList() {
            mList.clear()
        }

        internal fun createWrapperList(): List<T>? {
            val list = getOriginList()
            if (list == null || list.size <= 1) {
                return list
            }
            /*
             * addFirstAndLast:把第一个拷贝并添加到List结尾，并把最后一个拷贝并添加到List开头. <br/>
             * 如：原列表：0,1,2,3，则把3拷贝到0之前并把0拷贝到3之后变成：3,0,1,2,3,0
             */
            val first = list[0]
            val last = list[list.size - 1]

            // 最后一个和下标为1的item相等，表示已经添加过了(防止外部调用忘记调用clearList就直接调用此方法)
            if (list.size > 2 && last === list[1]) {
                return mList
            }

            mList.add(0, last)
            mList.addAll(list)
            mList.add(first)

            return mList
        }

        abstract fun getOriginList(): List<T>?
        abstract fun createView(banner: T, position: Int): View
        abstract fun bindDataToView(view: View, banner: T, position: Int)
    }
}