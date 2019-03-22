package com.xinye.architecture.stateful;

import android.support.annotation.IntDef;

/**
 * View状态
 *
 * @author wangheng
 */
public class ViewStateful implements IStateful {
   public static final int STATE_INVALID = 1;
   public static final int STATE_ATTACHED = 2;
   public static final int STATE_DETACHED = 3;

   private int mCurrentState = STATE_INVALID;


   @IntDef({STATE_INVALID,STATE_ATTACHED,STATE_DETACHED})
   private @interface ViewState{}

   public void setCurrentState(@ViewState int state){
       mCurrentState = state;
   }

    @Override
    public boolean canPerformFragmentTransaction() {
        return mCurrentState== STATE_ATTACHED;
    }

    @Override
    public boolean canPerformDialog() {
        return  mCurrentState== STATE_ATTACHED;
    }

    @Override
    public boolean canPerformUpdateView() {
        return true;
    }

    @Override
    public boolean isUIExists() {
        return mCurrentState == STATE_ATTACHED;
    }
}
