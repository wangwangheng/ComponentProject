package com.xinye.architecture.stateful;

import android.support.annotation.IntDef;

/**
 * Activity状态
 *
 * @author wangheng
 */
public class ActivityStateful implements IStateful {
    public static final int STATE_INVALID = 1;
    public static final int STATE_CREATED = 2;
    public static final int STATE_STARTED = 3;
    public static final int STATE_RESTARTED = 4;
    public static final int STATE_RESUME = 5;
    public static final int STATE_PAUSED = 6;
    public static final int STATE_STOPPED = 7;
    public static final int STATE_DESTROYED = 8;

    private int mCurrentState = STATE_INVALID;

    @IntDef({STATE_INVALID, STATE_CREATED, STATE_STARTED, STATE_RESTARTED, STATE_RESUME, STATE_PAUSED, STATE_STOPPED, STATE_DESTROYED})
    private @interface ActivityState {
    }

    public void setCurrentState(@ActivityState int state) {
        this.mCurrentState = state;
    }

    @Override
    public boolean canPerformFragmentTransaction() {
        return mCurrentState != STATE_INVALID && mCurrentState != STATE_DESTROYED;
    }

    @Override
    public boolean canPerformDialog() {
        return mCurrentState != STATE_INVALID && mCurrentState != STATE_DESTROYED;
    }

    @Override
    public boolean canPerformUpdateView() {
        return mCurrentState != STATE_INVALID && mCurrentState != STATE_DESTROYED;
    }

    @Override
    public boolean isUIExists() {
        return mCurrentState != STATE_INVALID && mCurrentState != STATE_DESTROYED;
    }
}
