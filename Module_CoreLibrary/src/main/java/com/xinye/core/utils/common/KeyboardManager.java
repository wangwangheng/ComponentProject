package com.xinye.core.utils.common;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.xinye.core.App;


/**
 * 软键盘显示、隐藏管理器
 *
 * @author wangheng
 */
public class KeyboardManager {
    private KeyboardManager() {

    }

    /**
     * 显示软键盘
     *
     */
    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏软键盘
     *
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 判断软键盘是否显示
     */
    public static boolean isActive(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    public static void hideKeyboardIfNeed(Activity activity,MotionEvent ev){
        if(activity == null || ev == null || ev.getAction() != MotionEvent.ACTION_DOWN){
            return;
        }
        View v = activity.getCurrentFocus();
        if(v == null){
            return;
        }
        if (isShouldHideKeyboard(v, ev)) {
            hideKeyboard(v.getWindowToken());
        }
    }

    private static boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if(v == null){
            return false;
        }
        if (v instanceof EditText) {
            int[] location = {0, 0};
            v.getLocationInWindow(location);
            int left = location[0],
                    top = location[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    private static void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) App.INSTANCE.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
