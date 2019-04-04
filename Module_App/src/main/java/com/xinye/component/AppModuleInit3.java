package com.xinye.component;

import android.content.Context;
import com.xinye.core.IAppLifecycle;
import com.xinye.core.log.Logger;
import com.xinye.lib_annotation.AppLifecycle;


@AppLifecycle
public class AppModuleInit3 implements IAppLifecycle {
    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void onCreate(Context context) {

        Logger.i(TAG,"#######AppModuleInit3@@@@@@@@@::onCreate");

    }

    @Override
    public void onTerminate() {

        Logger.i(TAG,"######AppModuleInit3@@@@@@@::onTerminate");

    }
}
