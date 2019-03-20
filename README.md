# ComponentProject

## 1. 各个模块怎样区分application还是library?

通过`gradle.properties`中配置的`MODE_MODULE = true`的值来控制模块的`build.gradle的配置，例子:

```
if(MODE_MODULE.toBoolean()) {
    apply plugin: 'com.android.library'
}else{
    apply plugin: 'com.android.application'
}
```

## 2.各怎样获取全局Context？

* 首先在*CoreLibrary*中创建一个`BaseApplication`，通过`BaseApplication`来给`App`这个单例的`mContext`赋值
* 各模块集成`BaseApplication`并在`AndroidManifest.xml`中配置这个`Application`

这样，无论是在集成模式还是在独立开发模式，都能保证`App`中的`mContext`对象不为空

```
abstract class BaseApplication: Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        App.getInstance().init(this)
    }
}
```

## 3.集成模式怎么去掉各模块的`Application`对象并采用自己的`AndroidManifest.xml`？

在各模块指定`AndroidManifest.xml`的路径和忽略的文件(暂时没办法忽略`.kt`的kotlin文件)

```
android {
    compileSdkVersion 28

    sourceSets {
        main {
            if(MODE_MODULE){
                manifest.srcFile 'src/main/module/AndroidManifest.xml'
                java {
                    setIncludes(new HashSet(['src/main/java/debug/**']))
                }
            }else{
                manifest.srcFile 'src/main/AndroidManifest.xml'
            }
        }
    }
}
```

其中*debug*和*module*都是自己创建的文件夹
