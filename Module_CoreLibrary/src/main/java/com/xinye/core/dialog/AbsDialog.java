package com.xinye.core.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.xinye.core.App;
import com.xinye.core.R;
import com.xinye.core.utils.app.DeviceUtils;
import com.xinye.core.utils.app.PixelUtil;


/**
 * 自定义Dialog基类
 *
 * @author wangheng
 */
public abstract class AbsDialog extends Dialog {


    public AbsDialog(Context context) {
        super(context, R.style.DialogStyle);
        initDialog(context);
    }

    protected AbsDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, R.style.DialogStyle);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);

        initDialog(context);
    }

    private void initDialog(Context context) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if(window != null) {

            if(window.getDecorView() != null){
                window.getDecorView().setPadding(0,0,0,0);
            }

            WindowManager.LayoutParams lp = window.getAttributes();
            window.setGravity(Gravity.CENTER);

            int screenWidth = DeviceUtils.getScreenWidth();

            lp.width = screenWidth - 2 * PixelUtil.dip2px(30); // 宽度
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度

            window.setAttributes(lp);
        }
    }

    @Override
    public void show() {
        if(isShowing()){
            return;
        }
        super.show();
    }

    @Override
    public void dismiss() {
        if(!isShowing()){
            return;
        }
        super.dismiss();
    }

}
