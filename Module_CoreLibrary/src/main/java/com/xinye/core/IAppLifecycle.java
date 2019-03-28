package com.xinye.core;

import android.content.Context;

public interface IAppLifecycle {
    int MAX_PRIORITY = 10;
    int MIN_PRIORITY = 1;
    int NORM_PRIORITY = 5;

    int getPriority();
    void onCreate(Context context);
    void onTerminate();
}
