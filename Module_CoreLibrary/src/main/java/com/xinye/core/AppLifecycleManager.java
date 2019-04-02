package com.xinye.core;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.xinye.core.log.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

public class AppLifecycleManager {

    private static final String TAG = "AppLifecycleManager";

    private static ArrayList<IAppLifecycle> sList = new ArrayList<>();

    private AppLifecycleManager(){}

    private static final class InstanceGenerator {
        private static final AppLifecycleManager INSTANCE = new AppLifecycleManager();
    }

    public static AppLifecycleManager getInstance(){
        return InstanceGenerator.INSTANCE;
    }

    public void init(Context context){
        sList.clear();
//        scanClassFile(context);
        addClassFile();
        Collections.sort(sList,new Comparator<IAppLifecycle>() {
            @Override
            public int compare(IAppLifecycle o1, IAppLifecycle o2) {
                return o2.getPriority() - o1.getPriority();
            }
        });
    }

    //通过反射去加载 ILifecycle 类的实例
    private static void registerAppLifecycle(String className) {
        if (TextUtils.isEmpty(className))
            return;
        try {
            Object obj = Class.forName(className).getConstructor().newInstance();
            if (obj instanceof IAppLifecycle) {
                sList.add((IAppLifecycle) obj);
            }
        } catch (Exception e) {
            Logger.e(TAG, className + "加载出错:" + e);
        }
    }

    private void addClassFile() {

    }

    public void onCreate(Context context){
        for(IAppLifecycle lifecycle: sList){
            lifecycle.onCreate(context);
        }
    }

    public void onTerminate() {
        for (IAppLifecycle like : sList) {
            like.onTerminate();
        }
    }

    private static void scanClassFile(Context context) {
        ApplicationInfo appInfo = null;
        String className = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = appInfo.metaData;
            if(bundle == null){
                Logger.e(TAG, "AndroidManifest.xml的Application级别中没有配置任何meta-data数据");
                return;
            }
            Set<String> set = bundle.keySet();
            for(String key : set){

                if(key != null && key.startsWith("Lifecycle-")){
                    className = bundle.getString(key);
                    if(className == null || className.length() == 0){
                        Logger.e(TAG, key + "配置的类名为[" + className + "]");
                        continue;
                    }
                    try {
                        Class clazz = Class.forName(className, true, context.getClassLoader());
                        Object obj = clazz.newInstance();
                        if (!(obj instanceof IAppLifecycle)) {
                            Logger.e(TAG, className + "没有实现IAppLifecycle接口");
                            continue;
                        }
                        IAppLifecycle lifecycle = (IAppLifecycle) obj;
                        sList.add(lifecycle);

                        Logger.e(TAG, className + "已添加到初始列表");
                    }catch (Exception e) {

                        Logger.e(TAG, className + "加载出错:" + e);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(TAG, "加载PackageManager失败:" + e);
        }
    }
}
