package com.xinye.core.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.xinye.core.App;
import com.xinye.core.log.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Bug解决工具类
 *
 * @author wangheng
 */
public class BugFixedUtils {

    /**
     * 解决MediaSessionLegacyHelper单例导致的Context内存泄漏问题
     * <p>
     * issue:https://github.com/android/platform_frameworks_base/commit/9b5257c9c99c4cb541d8e8e78fb04f008b1a9091
     * <p>
     * 解决方案:在Application中提前调用getHelper方法，并以Application的Context为参数
     */
    public static void fixedMediaSessionLegacyHelperMemoryLeak() {
        try {
            @SuppressLint("PrivateApi")
            Class clazz = Class.forName("android.media.session.MediaSessionLegacyHelper");
            Method method = clazz.getMethod("getHelper", Context.class);
            method.setAccessible(true);
            method.invoke(null, App.INSTANCE.getContext());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * references : http://blog.csdn.net/sodino/article/details/32188809
     * references : https://github.com/square/leakcanary/issues/2
     * references : https://github.com/square/leakcanary/issues/1
     *
     * @param destContext context
     */
    public static void fixInputMethodManagerMemoryLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (String param : arr) {
            try {
                f = imm.getClass().getDeclaredField(param);
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        Logger.d(BugFixedUtils.class.getSimpleName(),
                                "fixInputMethodManagerLeak break, context is not suitable, get_context="
                                        + v_get.getContext() + " dest_context=" + destContext);
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
