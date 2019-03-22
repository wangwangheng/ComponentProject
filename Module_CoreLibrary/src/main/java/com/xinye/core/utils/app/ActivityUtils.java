package com.xinye.core.utils.app;

import android.app.Activity;
import android.view.WindowManager;

/**
 * ActivityUtils
 *
 * @author wangheng
 */
public class ActivityUtils {
    /***
     * 设置Activity全屏
     *
     * @param activity activity
     */
    public static void setFullScreen(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(params);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }

    /***
     * 取消Activity全屏
     *
     * @param activity activity
     */
    public static void setNotFullScreen(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(params);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}
