package com.xinye.architecture.mvp;

import android.os.Bundle;
import com.xinye.architecture.base.BaseActivity;
import com.xinye.core.log.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * MVP的Activity的基类.
 *
 * @author wangheng
 */
public abstract class BaseMVPActivity<P extends IPresenter> extends BaseActivity {

    protected P mPresenter;

    public BaseMVPActivity(){
        this.mPresenter = createPresenter();
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {

        onCreateBeforeCallSuper(savedInstanceState);

        super.onCreate(savedInstanceState);

        mPresenter.init(BaseMVPActivity.this, this);
        onCreateExecute(savedInstanceState);
        mPresenter.onUICreate(savedInstanceState);
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
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onUIStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onUIResume();
    }

    @Override
    protected void onPause() {
        mPresenter.onUIPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mPresenter.onUIStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onUIDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mPresenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mPresenter.onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }
}
