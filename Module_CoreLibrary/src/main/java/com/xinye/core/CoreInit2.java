package com.xinye.core;

import android.content.Context;
import com.xinye.core.log.Logger;
import com.xinye.lib_annotation.AppLifecycle;

@AppLifecycle
public class CoreInit2 implements IAppLifecycle {

    @Override
    public int getPriority() {
        return NORM_PRIORITY;
    }

    @Override
    public void onCreate(Context context) {
        Logger.i(TAG,"****CoreInit222::onCreate");
    }

    @Override
    public void onTerminate() {
        Logger.i(TAG,"****CoreInit22222::onTerminate");
    }
}
