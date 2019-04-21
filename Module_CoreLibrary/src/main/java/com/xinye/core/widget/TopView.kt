package com.xinye.core.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.xinye.core.R

/**
 * 顶部栏View,右边的图片和文字只能显示一个，否则会重叠显示
 *
 * @author wangheng
 */
class TopView: RelativeLayout {
    constructor(context: Context?) : super(context){
        initView(context,null)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initView(context,attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        initView(context,attrs)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ){
        initView(context,attrs)
    }

    private var mLeftImageView: ImageView? = null
    private var mRightImageView: ImageView? = null
    private var mTitleTextView: TextView? = null
    private var mRightTextView: TextView? = null

    private fun initView(ctx: Context?, attrs: AttributeSet?){

        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_top_view,this)

        mLeftImageView = findViewById(R.id.iv_top_view_left_image)
        mRightImageView = findViewById(R.id.iv_top_view_right_image)
        mTitleTextView = findViewById(R.id.tv_top_view_title)
        mRightTextView = findViewById(R.id.tv_top_view_right_text)


        if (ctx != null && attrs != null){
            val ta = ctx.obtainStyledAttributes(attrs, R.styleable.TopView)

            val leftImage = ta.getDrawable(R.styleable.TopView_topViewLeftImage)
            mLeftImageView?.setImageDrawable(leftImage)


            val title = ta.getString(R.styleable.TopView_topViewTitle) ?: ""
            mTitleTextView?.text = title

            val rightText = ta.getString(R.styleable.TopView_topViewRightText) ?: ""
            mRightTextView?.text = rightText

            val rightImage = ta.getDrawable(R.styleable.TopView_topViewRightImage)
            mRightImageView?.setImageDrawable(rightImage)

            ta.recycle()
        }

    }

    fun setTitle(@StringRes resId: Int) {
        mTitleTextView?.setText(resId)
    }

    fun setTitle(title: String?){
        if(title == null || title.isEmpty()){
            mTitleTextView?.text = ""
        }else{
            mTitleTextView?.text = title
        }
    }

    fun setRightText(@StringRes resId: Int) {
        mRightTextView?.setText(resId)
        mRightImageView?.visibility = View.GONE
    }

    fun setRightText(rightText: String?){
        if(rightText == null || rightText.isEmpty()){
            mRightTextView?.text = ""
        }else{
            mRightTextView?.text = rightText
            mRightImageView?.visibility = View.GONE
        }
    }

    fun setLeftImageResource(@DrawableRes drawable: Int) {
        mLeftImageView?.setImageResource(drawable)
    }

    fun setLeftImageDrawable(drawable: Drawable?){
        mLeftImageView?.setImageDrawable(drawable)
    }

    fun setRightImageResource(@DrawableRes drawable: Int) {
        mRightImageView?.setImageResource(drawable)
        mRightTextView?.visibility = View.GONE
    }

    fun setRightImageDrawable(drawable: Drawable?){
        mRightImageView?.setImageDrawable(drawable)
        if(drawable != null){
            mRightTextView?.visibility = View.GONE
        }
    }

    fun setLeftImageClickListener(body: (view: View) -> Unit) {
        setViewClickListener(mLeftImageView,body)
    }

    fun setLeftImageClickListener(listener: OnClickListener) {
        setViewClickListener(mLeftImageView,listener)
    }

    fun setRightImageClickListener(body: (view: View) -> Unit) {
        setViewClickListener(mRightImageView,body)
    }

    fun setRightImageClickListener(listener: OnClickListener) {
        setViewClickListener(mRightImageView,listener)
    }

    fun setTitleClickListener(body: (view: View) -> Unit) {
        setViewClickListener(mTitleTextView,body)
    }

    fun setTitleClickListener(listener: OnClickListener) {
        setViewClickListener(mTitleTextView,listener)
    }

    fun setRightTextClickListener(body: (view: View) -> Unit) {
        setViewClickListener(mRightTextView,body)
    }

    fun setRightTextClickListener(listener: OnClickListener) {
        setViewClickListener(mRightTextView,listener)
    }

    /**
     * 设置View的点击事件
     */
    private fun setViewClickListener(view: View?,body: (view: View) -> Unit){
        view?.setOnClickListener(body)
    }

    /**
     * 设置View的点击事件
     */
    private fun setViewClickListener(view: View?,listener: OnClickListener) {
        if(view != null){
            view.setOnClickListener { listener.onClick(view) }
        }
    }
}