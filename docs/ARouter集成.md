# ARouter集成

## 集成步骤

* `api 'com.alibaba:arouter-api:x.x.x'`已经添加到**Module_CoreLibrary**模块，所以具体的模块并不需要此项配置
* `classpath "com.alibaba:arouter-register:$AROUTER_REGISTER_VERSION"`已添加到Project根目录下，具体的模块也不需要配置
* 具体的模块需要配置以下内容:
   * 插件`apply plugin: 'kotlin-kapt'`
   * `defaultConfig`中配置如下内容

    ```
    javaCompileOptions {
        annotationProcessorOptions {
            arguments += [AROUTER_MODULE_NAME: project.getName(), AROUTER_GENERATE_DOC: "enable"]
        }
    }
    ```
   * 在**dependencies**中添加`kapt "com.alibaba:arouter-compiler:$AROUTER_COMPILER_VERSION"`

## ARouter参考文档

[ARouter参考文档](https://github.com/alibaba/ARouter/blob/master/README_CN.md)