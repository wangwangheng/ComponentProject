package com.xinye.core.widget.roundrect;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.xinye.core.R;


/**
 * 圆角帮助类
 *
 * @author wangheng
 */
public class RoundRectHelper {

    private ViewGroup mTargetView;
    private Paint mLayerPaint;
    private int mRadius = 0;

    private Paint mMaskPaint;
    private RectF mMaskRect = null;
    private PorterDuffXfermode mMode;

    private int mBackgroundColor = Color.TRANSPARENT;

    // 圆角Path
    private Path mRoundRectCornerPath;

    public RoundRectHelper(Context context, AttributeSet attrs, ViewGroup targetView) {

        if(context == null){
            throw new IllegalArgumentException("context cannot equals null");
        }

        if(targetView == null){
            throw new IllegalArgumentException("targetView cannot equals null");
        }


        if(attrs != null){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundRectLayout);

            mRadius = ta.getDimensionPixelOffset(R.styleable.RoundRectLayout_roundRectRadius,0);
            mBackgroundColor = ta.getColor(R.styleable.RoundRectLayout_roundRectBackground,Color.TRANSPARENT);

            ta.recycle();
        }

        mTargetView = targetView;

        mTargetView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);

        mMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMaskPaint.setStyle(Paint.Style.FILL);
        mMaskPaint.setColor(Color.WHITE);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

        mLayerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLayerPaint.setXfermode(null);
    }

    private int mOldTargetWidth;
    private int mOldTargetHeight;

    /**
     * 得到蒙版RectF
     * @return 蒙版Rect
     */
    private RectF getMaskRect(){

        if(mMaskRect == null || isTargetViewSizeChanged()){
            mMaskRect = new RectF(0, 0, mTargetView.getWidth(), mTargetView.getHeight());
        }
        return mMaskRect;
    }

    /**
     * TargetView的大小是否发生了改变
     * @return TargetView的大小是否发生了改变
     */
    private boolean isTargetViewSizeChanged(){
        return mTargetView.getWidth() != mOldTargetWidth || mTargetView.getHeight() != mOldTargetHeight;
    }

    public void startRoundRect(Canvas canvas){
        canvas.saveLayer(getMaskRect(),mLayerPaint,Canvas.ALL_SAVE_FLAG);
        canvas.drawColor(mBackgroundColor);
    }


    public void completedRoundRect(Canvas canvas){

        mMaskPaint.setXfermode(mMode);

        drawRoundRectCorner(canvas);

        mMaskPaint.setXfermode(null);

        canvas.restore();

        mOldTargetWidth = mTargetView.getWidth();
        mOldTargetHeight = mTargetView.getHeight();
    }

    /**
     * 绘制圆角
     * @param canvas canvas
     */
    private void drawRoundRectCorner(Canvas canvas) {
        if(mRoundRectCornerPath == null && mRadius > 0){
            mRoundRectCornerPath = new Path();
            mRoundRectCornerPath.moveTo(0, mRadius);
            mRoundRectCornerPath.lineTo(0, 0);
            mRoundRectCornerPath.lineTo(mRadius, 0);
            mRoundRectCornerPath.arcTo(new RectF(0, 0, mRadius * 2, mRadius * 2),
                    -90, -90);
            mRoundRectCornerPath.close();
        }

        if(mRoundRectCornerPath != null){

            // 左上角
            canvas.drawPath(mRoundRectCornerPath, mMaskPaint);

            // 右上角
            canvas.save();
            canvas.translate(mTargetView.getWidth(),0);
            canvas.scale(-1,1);
            canvas.drawPath(mRoundRectCornerPath,mMaskPaint);
            canvas.restore();

            // 左下角
            canvas.save();
            canvas.translate(0,mTargetView.getHeight());
            canvas.scale(1,-1);
            canvas.drawPath(mRoundRectCornerPath,mMaskPaint);
            canvas.restore();

            // 右下角
            canvas.save();
            canvas.translate(mTargetView.getWidth(),mTargetView.getHeight());
            canvas.scale(-1,-1);
            canvas.drawPath(mRoundRectCornerPath,mMaskPaint);
            canvas.restore();

        }
    }
}
