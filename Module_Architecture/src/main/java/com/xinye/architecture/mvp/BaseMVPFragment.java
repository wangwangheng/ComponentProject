package com.xinye.architecture.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xinye.architecture.base.BaseActivity;
import com.xinye.architecture.base.BaseFragment;


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
            mPresenter = createPresenter();
            getPresenter().init((BaseActivity) getActivity(), getUI());
            mRootView = onCreateViewExecute(inflater, container, savedInstanceState);
            getPresenter().onUICreate(savedInstanceState);
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
     * getUI:得到UI.一般都是Fragment或者Activity本身 <br/>
     *
     * @return ui
     */
    protected abstract IUI getUI();

    /**
     * createPresenter:创建一个Presenter，子类来实现，可以通过new的方式直接new出来一个. <br/>
     *
     * @return presenter
     */
    protected abstract P createPresenter();

    /**
     * getPresenter:子类应该通过这个方法拿到Presenter的实例，而不是通过变量拿到. <br/>
     *
     * @return presenter
     */
    protected final P getPresenter() {
        return mPresenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onUIStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onUIStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onUIResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onUIPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRootView != null && mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        getPresenter().onUIDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mData != null) {
            outState.putBundle(KEY_DATA, mData);
        }
        if (getPresenter() != null) {
            getPresenter().onSaveInstanceState(outState);
        }
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
