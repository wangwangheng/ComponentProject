package com.xinye.module_thirdplatform;

import android.content.Context;
import com.xinye.core.IAppLifecycle;
import com.xinye.lib_annotation.AppLifecycle;

@AppLifecycle
public class MyAppDelegate implements IAppLifecycle {
    @Override
    public int getPriority() {
        return NORM_PRIORITY;
    }

    @Override
    public void onCreate(Context context) {

    }

    @Override
    public void onTerminate() {

    }
}
