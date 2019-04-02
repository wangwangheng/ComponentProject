package com.xinye.core;

import android.content.Context;
import com.xinye.core.log.Logger;
import com.xinye.lib_annotation.AppLifecycle;

@AppLifecycle
public class CoreInit implements IAppLifecycle {

    @Override
    public int getPriority() {
        return NORM_PRIORITY;
    }

    @Override
    public void onCreate(Context context) {
        Logger.i(TAG,"CoreInit::onCreate");
    }

    @Override
    public void onTerminate() {
        Logger.i(TAG,"CoreInit::onTerminate");
    }
}
