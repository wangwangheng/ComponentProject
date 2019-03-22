package com.xinye.core.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.xinye.core.R;
import com.xinye.core.utils.app.DeviceUtils;
import com.xinye.core.utils.app.PixelUtil;


/**
 * 进度条对话框Dialog
 *
 * @author wangheng
 */
public class ProgressDialog extends AbsDialog {


    public ProgressDialog(Context context) {
        super(context);
        init(context);
    }



    protected ProgressDialog(Context context,
                             boolean cancelable,
                             OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);

        Window window = getWindow();
        if(window != null) {

            if(window.getDecorView() != null){
                window.getDecorView().setPadding(0,0,0,0);
            }

            WindowManager.LayoutParams lp = window.getAttributes();
            window.setGravity(Gravity.CENTER);

            int screenWidth = DeviceUtils.getScreenWidth();

            int size = PixelUtil.dip2px(96);

            lp.width = size; // 宽度
            lp.height = size; // 高度

            window.setAttributes(lp);
        }

        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }
}
