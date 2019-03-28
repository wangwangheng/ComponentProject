package com.xinye.lib_annotation_processor;

import com.xinye.lib_annotation.LifecycleConfig;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class LifecycleProxyGenerator {
    private Elements mElementUtils;
    private TypeElement mTypeElement;
    private String mProxyClassSimpleName;

    public LifecycleProxyGenerator(Elements elements, TypeElement typeElement){
        mElementUtils = elements;
        mTypeElement = typeElement;

        mProxyClassSimpleName = LifecycleConfig.PROXY_CLASS_PREFIX
                + typeElement.getSimpleName().toString()
                + LifecycleConfig.PROXY_CLASS_SUFFIX;
    }

    public String getProxyClassFullName(){
        return LifecycleConfig.PROXY_CLASS_PACKAGE_NAME + "." + mProxyClassSimpleName;
    }

    public String generatorClass(){
        StringBuilder builder = new StringBuilder();
        // package xxxx;
        builder.append("package ").append(LifecycleConfig.PROXY_CLASS_PACKAGE_NAME).append(";\n\n");

        // import xxx;
        builder.append("import android.content.Context;\n");
        builder.append("import com.xinye.core.IAppLifecycle;\n");
        builder.append("import ").append(mTypeElement.getQualifiedName()).append(";\n\n");
        // public class implement IAppLike {
        //
        // }
        builder.append("public class ").append(mProxyClassSimpleName).append(" implements IAppLifecycle {\n\n");
        // private XXX mAppLike;
        builder.append("    private ").append(mTypeElement.getSimpleName().toString()).append(" mAppLike;\n\n");

        // constructor
        builder.append("    public ").append(mProxyClassSimpleName).append("(){\n");
        builder.append("        mAppLike = new ").append(mTypeElement.getSimpleName().toString()).append("();\n");
        builder.append("    }\n\n");// constructor completed

        // onCreate函数
        builder.append("    public void onCreate(Context context){\n");
        builder.append("        mAppLike.onCreate(context);\n");
        builder.append("    }\n\n");// onCreate()完成

        // getPriority()函数
        builder.append("    public int getPriority(){\n");
        builder.append("        return mAppLike.getPriority();\n");
        builder.append("    }\n\n");// getPriority()完成

        // onTerminate()函数
        builder.append("    public void onTerminate(){\n");
        builder.append("        mAppLike.onTerminate();\n");
        builder.append("    }\n\n");

        builder.append("}\n");
        return builder.toString();
    }



}

