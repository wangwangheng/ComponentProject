package com.xinye.architecture.mvp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.xinye.architecture.base.BaseActivity;
import retrofit2.Call;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Presenter的基类.
 *
 * @author wangheng
 */
public abstract class BasePresenter<U extends IUI> implements IPresenter {

    protected Handler mHandler = new Handler(Looper.getMainLooper());
    private U mUI;
    private BaseActivity mActivity;
    private ArrayList<WeakReference<Call>> mCallList;


    public BasePresenter() {
        mCallList = new ArrayList<>();
    }

    /**
     * 添加call
     *
     * @param call call
     */
    protected void addCallToCache(Call call) {
        if (isUIDestroyed()) {
            if (call != null) {
                call.cancel();
            }
            return;
        }
        if (mCallList != null) {
            mCallList.add(new WeakReference<>(call));
        }
    }

    protected void removeCallFromCache(Call call){
        ArrayList<WeakReference<Call>> list = mCallList;
        if(call != null && list != null){
            list.remove(call);
        }
    }

    private void clearAndCancelCallList() {
        ArrayList<WeakReference<Call>> list = mCallList;
        if (list != null) {
            Iterator<WeakReference<Call>> iterator = list.iterator();
            if (iterator.hasNext()) {
                WeakReference<Call> wf = iterator.next();
                if (wf != null) {
                    Call call = wf.get();
                    if (call != null) {
                        call.cancel();
                    }
                }
            }
            list.clear();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IUI> void init(BaseActivity activity, T ui) {
        this.mActivity = activity;
        this.mUI = (U) ui;
    }

    protected final U getUI() {
        return mUI;
    }

    protected final BaseActivity getActivity() {
        return mActivity;
    }

    @Override
    public void onUICreate(Bundle savedInstanceState) {
    }

    @Override
    public void onUIStart() {

    }

    @Override
    public void onUIResume() {

    }

    @Override
    public void onUIPause() {

    }

    @Override
    public void onUIStop() {

    }

    @Override
    public void onUIDestroy() {
        // 清空Call列表，并放弃Call请求
        clearAndCancelCallList();

        // clear handler
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }

        mUI = null;
        mActivity = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    /**
     * 耗时操作执行完之后，UI是否destroy的条件判断
     *
     * @return getUI()返回空，getActivity返回空或者isActivityDestroyed()
     */
    protected boolean isUIDestroyed() {
        return getUI() == null || getActivity() == null || getUI().isUIExists();
    }
}
