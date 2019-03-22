package com.xinye.architecture.mvp;

import android.os.Bundle;
import com.xinye.architecture.base.BaseActivity;


/**
 * MVP的Activity的基类.
 *
 * @author wangheng
 */
public abstract class BaseMVPActivity<P extends IPresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {

        onCreateBeforeCallSuper(savedInstanceState);

        super.onCreate(savedInstanceState);

        this.mPresenter = createPresenter();
        getPresenter().init(BaseMVPActivity.this, getUI());
        onCreateExecute(savedInstanceState);
        getPresenter().onUICreate(savedInstanceState);
    }

    protected void onCreateBeforeCallSuper(Bundle savedInstanceState) {

    }

    /**
     * onCreateExecute:所有BaseMVPActivity的子类不能再实现onCreate()方法，而是实现onCreateExecute()方法. <br/>
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void onCreateExecute(Bundle savedInstanceState);

    /**
     * createPresenter:创建一个Presenter，子类来实现，可以通过new的方式直接new出来一个. <br/>
     *
     * @return presenter
     */
    protected abstract P createPresenter();

    /**
     * getUI:得到UI层组件，一般都是Activity或者Fragment本身. <br/>
     *
     * @return presenter
     */
    protected abstract IUI getUI();

    /**
     * getPresenter:子类应该通过这个方法拿到Presenter的实例，而不是通过变量拿到. <br/>
     *
     * @return Presenter
     */
    protected final P getPresenter() {
        return mPresenter;
    }


    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().onUIStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onUIResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onUIPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().onUIStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().onUIDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (getPresenter() != null) {
            getPresenter().onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        getPresenter().onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }
}
