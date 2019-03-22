package com.xinye.core.log;

import android.util.Log;
import com.xinye.core.App;

/**
 * 统一管理logger类.
 *
 * @author wangheng
 */
public class Logger {

    // 是否需要打印log，可以在application的onCreate函数里面初始化
    private static final boolean isDebug = App.INSTANCE.isDebug();

    private static final String TAG = "Logger";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (needLog(msg)) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (needLog(msg)) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (needLog(msg)) {
            Log.e(TAG, msg);
        }
    }

    public static void v(String msg) {
        if (needLog(msg)) {
            Log.v(TAG, msg);
        }
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (needLog(msg)) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (needLog(msg)) {
            Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (needLog(msg)) {
            Log.v(tag, msg);
        }
    }

    public static void e(String tag, String logPrefix, Throwable throwable) {
        if (needLog(logPrefix + ":" + throwable)) {
            Log.e(tag, logPrefix + ":" + throwable);
        }
    }

    private static boolean needLog(String message) {
        return isDebug && null != message;
    }

}
