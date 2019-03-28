package com.xinye.component;

import android.content.Context;
import com.xinye.core.IAppLifecycle;
import com.xinye.lib_annotation.AppLifecycle;


@AppLifecycle
public class TestProcessor implements IAppLifecycle {
    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void onCreate(Context context) {

    }

    @Override
    public void onTerminate() {

    }
}
