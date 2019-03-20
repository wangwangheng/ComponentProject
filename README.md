# ComponentProject

## 各个模块怎样区分application还是library?

通过`gradle.properties`中配置的`MODE_MODULE = true`的值来控制模块的`build.gradle的配置，例子:

```
if(MODE_MODULE.toBoolean()) {
    apply plugin: 'com.android.library'
}else{
    apply plugin: 'com.android.application'
}
```

