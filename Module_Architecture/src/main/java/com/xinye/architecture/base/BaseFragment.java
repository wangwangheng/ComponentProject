package com.xinye.architecture.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xinye.architecture.R;
import com.xinye.architecture.mvp.IUI;
import com.xinye.architecture.stateful.FragmentStateful;
import com.xinye.core.dialog.DialogHelper;


/**
 * Fragment基类
 *
 * @author wangheng
 */
public abstract class BaseFragment extends Fragment implements IUI {


    private FragmentStateful mStateful = new FragmentStateful();

    private ProgressDialog mWaitDialog;
    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mStateful.setCurrentState(FragmentStateful.STATE_VIEW_CREATED);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mStateful.setCurrentState(FragmentStateful.STATE_ATTACHED);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mStateful.setCurrentState(FragmentStateful.STATE_ATTACHED);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStateful.setCurrentState(FragmentStateful.STATE_CREATED);
    }

    @Override
    public void onStart() {
        super.onStart();
        mStateful.setCurrentState(FragmentStateful.STATE_STARTED);
    }

    @Override
    public void onResume() {
        super.onResume();
        mStateful.setCurrentState(FragmentStateful.STATE_RESUMED);

    }

    @Override
    public void onPause() {
        mStateful.setCurrentState(FragmentStateful.STATE_PAUSED);
        super.onPause();
    }

    @Override
    public void onStop() {
        mStateful.setCurrentState(FragmentStateful.STATE_STOPPED);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        mStateful.setCurrentState(FragmentStateful.STATE_VIEW_DESTROYED);
        hideWaitDialog();
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
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mStateful.setCurrentState(FragmentStateful.STATE_DESTROYED);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        mStateful.setCurrentState(FragmentStateful.STATE_DETACHED);
        hideWaitDialog();
        super.onDetach();
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
    /**
     * @param isVisibleToUser 是否对用户可见
     * @deprecated 绝对不要调用这个方法，否则可能导致卡死
     */
    @Override
    public final void setUserVisibleHint(final boolean isVisibleToUser) {
        if (mHandler != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Activity activity = getActivity();
                    if (activity == null || activity.isFinishing()) {
                        return;
                    }
                    onUserVisibleHint(isVisibleToUser);
                }
            });
        }

        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * setUserVisibleHint 通过Handler#post的方式调用
     *
     * @param isVisibleToUser 是否对用户可见
     */
    protected void onUserVisibleHint(boolean isVisibleToUser) {

    }

    protected int getLayoutId() {
        return 0;
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
                if (mStateful.canPerformDialog() && !isHidden() && getActivity() != null) {
                    mWaitDialog = DialogHelper.getWaitDialog(getActivity(), message,listener);
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

    @Override
    public void finishActivity() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    protected String getPageName(){
        return "";
    }
}
