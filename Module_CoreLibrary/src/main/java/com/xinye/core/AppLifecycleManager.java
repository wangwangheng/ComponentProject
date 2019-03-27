package com.xinye.core;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AppLifecycleManager {

    private static ArrayList<IAppLike> sList = new ArrayList<>();

    public static void registerAppLike(IAppLike like){
        sList.add(like);
    }

    public static void init(Context context){
        Collections.sort(sList,new Comparator<IAppLike>() {
            @Override
            public int compare(IAppLike o1, IAppLike o2) {
                return o2.getPriority() - o1.getPriority();
            }
        });
        for(IAppLike like: sList){
            like.onCreate(context);
        }
    }

    public static void terminate() {
        for (IAppLike like : sList) {
            like.onTerminate();
        }
    }
}
