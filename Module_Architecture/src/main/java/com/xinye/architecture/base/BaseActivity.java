package com.xinye.architecture.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import com.xinye.architecture.R;
import com.xinye.architecture.mvp.IUI;
import com.xinye.architecture.stateful.ActivityStateful;
import com.xinye.core.dialog.DialogHelper;
import com.xinye.core.utils.common.KeyboardManager;


/**
 * 所有Activity的基类
 *
 * @author wangheng
 */
public abstract class BaseActivity extends AppCompatActivity implements IUI {

    private ProgressDialog mWaitDialog;
    private Handler mHandler = null;
    private ActivityStateful mStateful = new ActivityStateful();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStateful.setCurrentState(ActivityStateful.STATE_CREATED);
        mHandler = new Handler();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        mStateful.setCurrentState(ActivityStateful.STATE_RESTARTED);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStateful.setCurrentState(ActivityStateful.STATE_STARTED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStateful.setCurrentState(ActivityStateful.STATE_RESUME);
    }

    @Override
    protected void onPause() {
        mStateful.setCurrentState(ActivityStateful.STATE_PAUSED);
        super.onPause();
    }

    @Override
    protected void onStop() {
        mStateful.setCurrentState(ActivityStateful.STATE_STOPPED);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        hideWaitDialog();
        mStateful.setCurrentState(ActivityStateful.STATE_DESTROYED);
        if (mHandler != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mHandler != null) {
                        mHandler.removeCallbacksAndMessages(null);
                        mHandler = null;
                    }
                }
            });
        }
        super.onDestroy();
    }


    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            KeyboardManager.hideKeyboardIfNeed(this,ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void showWaitDialog(DialogInterface.OnDismissListener listener) {
        showWaitDialog(R.string.text_loading,listener);
    }

    @Override
    public void showWaitDialog(int resid,DialogInterface.OnDismissListener listener) {
        showWaitDialog(getString(resid),listener);
    }

    @Override
    public void showWaitDialog(final String message, final DialogInterface.OnDismissListener listener) {
        if (mHandler == null) {
            return;
        }
        // 隐藏对话框，一定不要放到runnable里面~
        hideWaitDialog();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mStateful.canPerformDialog()) {
                    mWaitDialog = DialogHelper.getWaitDialog(BaseActivity.this, message,listener);
                    mWaitDialog.setMessage(message);
                    mWaitDialog.show();
                }
            }
        });
    }

    @Override
    public void hideWaitDialog() {
        if (mHandler == null) {
            return;
        }
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (mStateful.canPerformDialog() && mWaitDialog != null && mWaitDialog.isShowing()) {
                    mWaitDialog.dismiss();
                    mWaitDialog = null;
                }
            }
        });
    }

    protected String getPageName(){
        return "";
    }

    @Override
    public boolean canPerformFragmentTransaction() {
        return mStateful.canPerformFragmentTransaction();
    }

    @Override
    public boolean canPerformDialog() {
        return mStateful.canPerformDialog();
    }

    @Override
    public boolean canPerformUpdateView() {
        return mStateful.canPerformUpdateView();
    }

    @Override
    public boolean isUIExists() {
        return mStateful.isUIExists();
    }
}
