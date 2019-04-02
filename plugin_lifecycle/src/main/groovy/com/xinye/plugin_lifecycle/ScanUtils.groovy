package com.xinye.plugin_lifecycle

import net.rubygrapefruit.platform.internal.NativeLibraryLoader

import java.util.jar.JarEntry
import java.util.jar.JarFile

class ScanUtils {
    static final String PROXY_CLASS_PREFIX = "Xinye\$\$"
    static final String PROXY_CLASS_SUFFIX = "\$\$Proxy.class"

    //注意class文件名中的包名是以“/”分隔开，而不是“.”分隔的，
    // 这个包名是我们通过APT生成的所有 IAppLifecycle代理类的包名
    static final String PROXY_CLASS_PACKAGE_NAME = "com/xinye/lifecycle/proxy"

    //AppLifeCycleManager是应用生命周期框架初始化方法调用类
    static final REGISTER_CLASS_FILE_NAME = "com/xinye/core/AppLifecycleManager.class"

    //包含生命周期管理初始化类的文件，即包含AppLifecycleManager类的class文件或者jar文件
    static File FILE_CONTAINS_INIT_CLASS

    // 判断该class是否是我们的目标类
    static boolean isTargetProxyClass(File file){
        String name = file.getName()
        return name.startsWith(PROXY_CLASS_PREFIX) && name.endsWith(PROXY_CLASS_SUFFIX)
    }

    static boolean shouldProcessPreDexJar(String path) {
        println("######shouldProcessPreDexJar $path")
        boolean notSupport = !path.contains("com.android.support")
        boolean notKotlin = !path.contains("kotlin-stdlib-jdk")
        boolean notM2Repository = !path.contains("/android/m2repository")
        return  notM2Repository && notSupport && notKotlin
    }

    /**
     * 扫描jar包里的所有class文件：
     * 1.通过包名识别所有需要注入的类名
     * 2.找到AppLifeCycleManager类所在的jar包，后面我们会在该jar包里进行代码注入
     */
    static List<String> scanJar(File jarFile, File destFile) {
        def file = new JarFile(jarFile)
        Enumeration<JarEntry> enumeration = file.entries()
        List<String> list = null
        while (enumeration.hasMoreElements()) {
            //遍历这个jar包里的所有class文件项
            JarEntry jarEntry = enumeration.nextElement()
            //class文件的名称，这里是全路径类名，包名之间以"/"分隔
            String entryName = jarEntry.getName()
            if (entryName == REGISTER_CLASS_FILE_NAME) {
                //标记这个jar包包含 AppLifeCycleManager.class
                //扫描结束后，我们会生成注册代码到这个文件里
                FILE_CONTAINS_INIT_CLASS = destFile
            } else {
                //通过包名来判断，严谨点还可以加上类名前缀、后缀判断
                //通过APT生成的类，都有统一的前缀、后缀
                if (entryName.startsWith(PROXY_CLASS_PACKAGE_NAME)) {
                    if (list == null) {
                        list = new ArrayList<>()
                    }
                    list.addAll(entryName.substring(entryName.lastIndexOf("/") + 1))
                }
            }
        }
        return list
    }


}