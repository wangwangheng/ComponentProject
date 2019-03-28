package com.xinye.core;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AppLifecycleManager {

    private static final String TAG = "AppLifecycleManager";

    private static ArrayList<IAppLifecycle> sList = new ArrayList<>();

    public static void registerAppLifecycle(IAppLifecycle like){
        sList.add(like);
    }

    public static void init(Context context){
        scanClassFile(context);
        Collections.sort(sList,new Comparator<IAppLifecycle>() {
            @Override
            public int compare(IAppLifecycle o1, IAppLifecycle o2) {
                return o2.getPriority() - o1.getPriority();
            }
        });
        for(IAppLifecycle lifecycle: sList){
            lifecycle.onCreate(context);
        }
    }

    public static void terminate() {
        for (IAppLifecycle like : sList) {
            like.onTerminate();
        }
    }

    private static void scanClassFile(Context context) {
//        try {
//            //扫描到所有的目标类
//            Set<String> set = getFileNameByPackageName(context, LifecycleConfig.PROXY_CLASS_PACKAGE_NAME);
//            if (set != null) {
//                for (String className : set) {
//                    try {
//                        //通过反射来加载实例化所有的代理类
//                        Object obj = Class.forName(className).newInstance();
//                        if (obj instanceof IAppLifecycle) {
//                            sList.add((IAppLifecycle) obj);
//                        }
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
