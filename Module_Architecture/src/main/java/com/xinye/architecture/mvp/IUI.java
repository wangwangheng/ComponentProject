package com.xinye.architecture.mvp;

import android.content.DialogInterface;
import android.support.annotation.StringRes;
import com.xinye.architecture.stateful.IStateful;

/**
 * MVP的View层协议.
 *
 * @author wangheng
 */
public interface IUI extends IStateful {
    /**
     * dismissWaitingDialogIfShowing:如果加载对话框正在显示，则dismiss掉它. <br/>
     */
    void hideWaitDialog();

    /**
     * 显示正在加载对话框. <br/>
     */
    void showWaitDialog(String message, DialogInterface.OnDismissListener listener);

    /**
     * 显示正在加载对话框. <br/>
     */
    void showWaitDialog(@StringRes int resId, DialogInterface.OnDismissListener listener);

    /**
     * 显示正在加载对话框. <br/>
     */
    void showWaitDialog(DialogInterface.OnDismissListener listener);

    /**
     * finishActivity
     */
    void finishActivity();
}
