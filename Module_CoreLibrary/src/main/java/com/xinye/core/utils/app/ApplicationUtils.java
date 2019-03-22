package com.xinye.core.utils.app;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;

/**
 * 应用程序工具类
 *
 * @author wangheng
 */
public class ApplicationUtils {

    private ApplicationUtils() {

    }

    /**
     * 当前应用的Activity是否在前台运行
     *
     * @param context context
     * @return 是否有UI在前台
     */
    public static boolean isProcessUIForeground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        @SuppressWarnings("deprecation")
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity != null && topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;

    }

}
