package com.xinye.core.widget.roundrect;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 圆角RelativeLayout
 *
 * @author wangheng
 */
public class RoundRectRelativeLayout extends RelativeLayout {

    public RoundRectRelativeLayout(Context context) {
        super(context);
        init(context,null);
    }

    public RoundRectRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public RoundRectRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private RoundRectHelper mHelper = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RoundRectRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mHelper = new RoundRectHelper(context,attrs,RoundRectRelativeLayout.this);
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
