package com.xinye.architecture.stateful;

import android.support.annotation.IntDef;

/**
 * Activity状态
 *
 * @author wangheng
 */
public class FragmentStateful implements IStateful {
    public static final int STATE_INVALID = 1;
    public static final int STATE_CREATED = 2;
    public static final int STATE_STARTED = 3;
    public static final int STATE_STOPPED = 4;
    public static final int STATE_VIEW_CREATED = 5;
    public static final int STATE_VIEW_DESTROYED = 6;
    public static final int STATE_DESTROYED = 7;
    public static final int STATE_ATTACHED = 8;
    public static final int STATE_DETACHED = 9;
    public static final int STATE_RESUMED = 10;
    public static final int STATE_PAUSED = 11;

    private int mCurrentState = STATE_INVALID;

    @IntDef({STATE_INVALID, STATE_CREATED, STATE_STARTED,
            STATE_STOPPED, STATE_VIEW_CREATED, STATE_VIEW_DESTROYED,
            STATE_DESTROYED, STATE_ATTACHED, STATE_DETACHED,
            STATE_RESUMED,STATE_PAUSED})
    private @interface FragmentState {
    }

    public void setCurrentState(@FragmentState int state) {
        this.mCurrentState = state;
    }

    @Override
    public boolean canPerformFragmentTransaction() {
        return mCurrentState != STATE_INVALID && mCurrentState != STATE_DESTROYED && mCurrentState != STATE_DETACHED && mCurrentState != STATE_VIEW_DESTROYED;
    }

    @Override
    public boolean canPerformDialog() {
        return mCurrentState != STATE_INVALID && mCurrentState != STATE_DESTROYED && mCurrentState != STATE_DETACHED && mCurrentState != STATE_VIEW_DESTROYED;
    }

    @Override
    public boolean canPerformUpdateView() {
        return mCurrentState != STATE_INVALID && mCurrentState != STATE_DESTROYED && mCurrentState != STATE_DETACHED && mCurrentState != STATE_VIEW_DESTROYED;
    }

    @Override
    public boolean isUIExists() {
        return mCurrentState != STATE_INVALID && mCurrentState != STATE_DESTROYED && mCurrentState != STATE_DETACHED && mCurrentState != STATE_VIEW_DESTROYED;
    }
}
