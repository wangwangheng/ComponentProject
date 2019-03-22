package com.xinye.architecture.mvp;

import android.os.Bundle;
import com.xinye.architecture.base.BaseActivity;


/**
 * MVP的Presenter层协议.
 *
 * @author wangheng
 */
public interface IPresenter {

    <U extends IUI> void init(BaseActivity activity, U ui);

    /**
     * onUICreate:UI被创建的时候应该invoke这个method. <br/>
     * <p/>
     * 比如Activity的onCreate()、Fragment的onCreateView()的方法应该调用Presenter的这个方法
     *
     * @param savedInstanceState 保存了的状态
     */
    void onUICreate(Bundle savedInstanceState);

    /**
     * onUIStart:在UI被创建和被显示到屏幕之间应该回调这个方法. <br/>
     * <p/>
     * 比如Activity的onStart()方法应该调用Presenter的这个方法
     */
    void onUIStart();

    /**
     * onUIResume:在UI被显示到屏幕的时候应该回调这个方法. <br/>
     * <p/>
     * 比如Activity的onResume()方法应该调用Presenter的这个方法
     */
    void onUIResume();

    /**
     * onUIPause:在UI从屏幕上消失的时候应该回调这个方法. <br/>
     * <p/>
     * 比如Activity的onPause()方法应该调用Presenter的这个方法
     */
    void onUIPause();

    /**
     * onUIStop:在UI从屏幕完全隐藏应该回调这个方法. <br/>
     * <p/>
     * 比如Activity的onStop()方法应该调用Presenter的这个方法
     */
    void onUIStop();

    /**
     * onUIDestroy:当UI被Destory的时候应该回调这个方法. <br/>
     */
    void onUIDestroy();

    /**
     * onSaveInstanceState:保存数据. <br/>
     * <p/>
     * 一般是因为内存不足UI的状态被回收的时候调用
     *
     * @param outState 待保存的状态
     */
    void onSaveInstanceState(Bundle outState);

    /**
     * onRestoreInstanceState:当UI被恢复的时候被调用. <br/>
     *
     * @param savedInstanceState 保存了的状态
     */
    void onRestoreInstanceState(Bundle savedInstanceState);
}
