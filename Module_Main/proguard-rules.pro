# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.xinye.core.IAppLifecycle
-keep class * implements com.xinye.core.IAppLifecycle {*;}
-keep class com.xinye.core.AppLifecycleManager {*;}
-keepclassmembers class com.xinye.core.AppLifecycleManager {*;}

-keep class javax.annotation.* {*;}
-keep class * extends android.graphics.drawable.Drawable {*;}

# okhttp3,okio
-dontwarn okio.**
-dontwarn okhttp3.**

-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn javax.annotation.concurrent.GuardedBy
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# 支持库
-keep class android.support.v4.** {*;}
-keep class android.support.*.** {*;}
-keep class android.arch.** {*;}
-keep class android.arch.*.** {*;}

#### ARouter
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
# -keep class * implements com.alibaba.android.arouter.facade.template.IProviders

-dontwarn javax.lang.model.element.Element
