package com.xinye.core.widget.roundrect;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 圆角FrameLayout
 *
 * @author wangheng
 */
public class RoundRectFrameLayout extends FrameLayout {

    public RoundRectFrameLayout(Context context) {
        super(context);
        init(context,null);
    }

    public RoundRectFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public RoundRectFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private RoundRectHelper mHelper = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RoundRectFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mHelper = new RoundRectHelper(context,attrs,RoundRectFrameLayout.this);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        mHelper.startRoundRect(canvas);

        // dst
        super.dispatchDraw(canvas);

        // src setXfermode
        mHelper.completedRoundRect(canvas);
    }
}
