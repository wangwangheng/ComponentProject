package com.xinye.core.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

/**
 * 等待对话框
 *
 * @author wangheng
 */

public class DialogHelper {
    /***
     * 获取一个耗时等待对话框
     *
     * @param context context
     * @param message message
     * @return ProgressDialog
     */
    public static ProgressDialog getWaitDialog(Context context, String message,
                                               DialogInterface.OnDismissListener listener) {
        ProgressDialog waitDialog = new ProgressDialog(context);
        if (!TextUtils.isEmpty(message)) {
            waitDialog.setMessage(message);
        }
        waitDialog.setOnDismissListener(listener);
        waitDialog.setCanceledOnTouchOutside(false);
        return waitDialog;
    }
}
