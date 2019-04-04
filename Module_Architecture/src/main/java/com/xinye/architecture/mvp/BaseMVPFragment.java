package com.xinye.architecture.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xinye.architecture.base.BaseActivity;
import com.xinye.architecture.base.BaseFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * MVP Fragment的基类.
 *
 * @author wangheng
 */
public abstract class BaseMVPFragment<P extends IPresenter> extends BaseFragment {

    private static final String KEY_DATA = "keyData";

    protected P mPresenter;

    protected View mRootView;

    protected Bundle mData;

    protected Bundle mExtras;

    public BaseMVPFragment(){
        mPresenter = createPresenter();
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Bundle bundle = savedInstanceState.getBundle(KEY_DATA);
            if (bundle != null) {
                mData = bundle;
            }
        }

        onBeforeCreateViewExecuteEveryTimes(savedInstanceState);
        if (mRootView == null) {
            mPresenter.init((BaseActivity) getActivity(), this);
            mRootView = onCreateViewExecute(inflater, container, savedInstanceState);
            mPresenter.onUICreate(savedInstanceState);
        }

        onAfterCreateViewExecuteEveryTimes(savedInstanceState);

        return mRootView;
    }

    protected void onAfterCreateViewExecuteEveryTimes(Bundle savedInstanceState) {

    }

    protected void onBeforeCreateViewExecuteEveryTimes(Bundle savedInstanceState) {
    }

    /**
     * onCreateViewExecute:MVP的Fragment不应该再实现onCreateView()方法，而是应该事先onCreateViewExecute()方法. <br/>
     *
     * @param inflater           inflater
     * @param container          container
     * @param savedInstanceState savedInstanceState
     * @return view
     */
    protected abstract View onCreateViewExecute(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * createPresenter:创建一个Presenter，子类来实现，可以通过new的方式直接new出来一个. <br/>
     *
     * @return presenter
     */
    protected P createPresenter(){
        ParameterizedType type = (ParameterizedType)(getClass().getGenericSuperclass());
        if(type == null){
            return null;
        }
        Type[] typeArray = type.getActualTypeArguments();
        if(typeArray.length == 0){
            return null;
        }
        Class<P> clazz = (Class<P>) typeArray[0];
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onUIStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onUIStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onUIResume();
    }

    @Override
    public void onPause() {
        mPresenter.onUIPause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if (mRootView != null && mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        mPresenter.onUIDestroy();
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mData != null) {
            outState.putBundle(KEY_DATA, mData);
        }
        mPresenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    /**
     * getData:得到传输过来的数据. <br/>
     *
     * @return data
     */
    public Bundle getData() {
        return mData;
    }

    /**
     * setData:设置启动这个Fragment必须的数据. <br/>
     *
     * @param bundle bundle
     */
    public void setData(Bundle bundle) {
        this.mData = bundle;
    }

    /**
     * getExtras:得到Extras. <br/>
     *
     * @return Extras
     */
    public Bundle getExtras() {
        return mExtras;
    }

    /**
     * setExtras:设置Extras. <br/>
     *
     * @param extras extras
     */
    public void setExtras(Bundle extras) {
        this.mExtras = extras;
    }
}
