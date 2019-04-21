package com.xinye.core;

import android.content.Context;
import com.alibaba.android.arouter.launcher.ARouter;
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

        if (App.INSTANCE.isDebug()) { // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(App.INSTANCE.getApplication()); // 尽可能早，推荐在Application中初始化
    }

    @Override
    public void onTerminate() {
        Logger.i(TAG,"CoreInit::onTerminate");
    }
}
