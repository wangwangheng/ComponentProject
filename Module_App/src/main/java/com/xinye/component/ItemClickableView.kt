package com.xinye.component

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * 可点击的ItemView
 *
 * @author wangheng
 */
class ItemClickableView: LinearLayout {
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
    private var mTextView: TextView? = null

    private fun initView(ctx: Context?, attrs: AttributeSet?){

        val inflater = ctx?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        inflater?.inflate(R.layout.layout_item_clickable,this)

        mLeftImageView = findViewById(R.id.iv_item_clickable_left)
        mRightImageView = findViewById(R.id.iv_item_clickable_right)
        mTextView = findViewById(R.id.tv_item_clickable_text)

        if(ctx != null && attrs != null){
            val ta = ctx.obtainStyledAttributes(attrs, R.styleable.ItemClickableView)

            val leftImage = ta.getDrawable(R.styleable.ItemClickableView_icvLeftImage)
            mLeftImageView?.setImageDrawable(leftImage)


            val text = ta.getString(R.styleable.ItemClickableView_icvText) ?: ""
            mTextView?.text = text

            val rightImage = ta.getDrawable(R.styleable.ItemClickableView_icvRightImage)
            mRightImageView?.setImageDrawable(rightImage)

            val textColor = ta.getColor(R.styleable.ItemClickableView_icvTextColor, Color.parseColor("#FF1B1B1B"))
            val textSize = ta.getDimensionPixelOffset(R.styleable.ItemClickableView_icvTextSize,0)
            val textMargin = ta.getDimensionPixelOffset(R.styleable.ItemClickableView_icvTextMargin,0)

            val params = mTextView?.layoutParams
            if(params != null && params is MarginLayoutParams && textMargin > 0){
                params.leftMargin = textMargin
                params.rightMargin = textMargin
                mTextView?.layoutParams = params
            }

            if(textSize > 0){
                mTextView?.textSize = textSize.toFloat()
            }

            mTextView?.setTextColor(textColor)


            val leftImageSize = ta.getDimensionPixelOffset(R.styleable.ItemClickableView_icvLeftImageSize,0)

            val leftImageParams = mLeftImageView?.layoutParams
            if(leftImageParams != null && leftImageSize > 0){
                leftImageParams.width = leftImageSize
                leftImageParams.height = leftImageSize
                mLeftImageView?.layoutParams = params
            }

            val rightImageSize = ta.getDimensionPixelOffset(R.styleable.ItemClickableView_icvRightImageSize,0)

            val rightImageParams = mRightImageView?.layoutParams
            if(rightImageParams != null && rightImageSize > 0){
                rightImageParams.width = rightImageSize
                rightImageParams.height = rightImageSize
                mRightImageView?.layoutParams = params
            }
            ta.recycle()
        }
    }
}