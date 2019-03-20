# ComponentProject

## 1. 各个模块怎样区分application还是library?

通过`gradle.properties`中配置的`MODE_MODULE = true`的值来控制模块的`build.gradle的配置，例子:

```
if(IS_LIBRARY.toBoolean()) {
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
            if(IS_LIBRARY){
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

## 4.依赖库重复问题怎么解决？

如果依赖库重复，则在编译时就会报错，解决办法就是找出那个多出来的库，并将多出来的库给排除掉，具体可以**根据组件名排除或者根据包名排除**

```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile("com.jude:easyrecyclerview:$rootProject.easyRecyclerVersion") {
        exclude module: 'support-v4'//根据组件名排除
        exclude group: 'android.support.v4'//根据包名排除
    }
}
```

如果是第三方库依赖，则建议都添加到CoreLibrary




