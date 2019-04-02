package com.xinye.plugin_lifecycle

import com.android.annotations.NonNull
import com.android.annotations.Nullable
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

class LifecycleTransform extends Transform {

    Project mProject

    LifecycleTransform(Project project){
        this.mProject = project
    }

    @Override
    String getName() {
        // 只是一个名字而已
        return "LifecycleTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        // 该Transform支持扫描的文件类型，分为class文件和资源文件，我们这里只处理class文件的扫描
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        // Transfrom 的扫描范围，我这里扫描整个工程，包括当前module以及其他jar包、aar文件等所有的class
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        // 是否增量扫描
        return false
    }

    @Override
    public void transform(
            @NonNull Context context,
            @NonNull Collection<TransformInput> inputs,
            @NonNull Collection<TransformInput> referencedInputs,
            @Nullable TransformOutputProvider outputProvider,
            boolean isIncremental) throws IOException, TransformException, InterruptedException {

        println("开始执行transform")
        def proxyClassList = []
        // inputs就是所有扫描到的class文件或者是jar包，一共2种类型
        inputs.each { TransformInput input ->

            //1.遍历所有的class文件目录
            input.directoryInputs.each { DirectoryInput directoryInput ->
                // 递归扫描该目录下所有的class文件
                if (directoryInput.file.isDirectory()) {

                    directoryInput.file.eachFileRecurse { File file ->
                        //形如 Xinye$$****$$Proxy.class 的类，是我们要找的目标class，直接通过class的名称来判断，也可以再加上包名的判断，会更严谨点
                        if (ScanUtils.isTargetProxyClass(file)) {
                            //如果是我们自己生产的代理类，保存该类的类名
                            proxyClassList.add(file.name)
                        }
                    }
                }

                //Transform扫描的class文件是输入文件(input)，有输入必然会有输出(output)，处理完成后需要将输入文件拷贝到一个输出目录下去，
                //后面打包将class文件转换成dex文件时，直接采用的就是输出目录下的class文件了。
                //必须这样获取输出路径的目录名称
                def dest = outputProvider.getContentLocation(
                        directoryInput.name,
                        directoryInput.contentTypes,
                        directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            // 2.遍历查找所有的jar包
            input.jarInputs.each { JarInput jarInput ->

                //与处理class文件一样，处理jar包也是一样，最后要将inputs转换为outputs
                def jarName = jarInput.name
                def md5 = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())

                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                //获取输出路径下的jar包名称，必须这样获取，得到的输出路径名不能重复，否则会被覆盖
                def dest = outputProvider.getContentLocation(jarName + md5, jarInput.contentTypes, jarInput.scopes, Format.JAR)

                if (jarInput.file.getAbsolutePath().endsWith(".jar")) {
                    File src = jarInput.file
                    //先简单过滤掉 support-v4 之类的jar包，只处理有我们业务逻辑的jar包
                    if (src != null && ScanUtils.shouldProcessPreDexJar(src.absolutePath)) {
                        // 扫描jar包的核心代码在这里，主要做2件事情：
                        // 1.扫描该jar包里有没有实现IAppLifecycle接口的代理类;
                        // 2.扫描AppLifeCycleManager这个类在哪个jar包里，并记录下来，后面需要在该类里动态注入字节码；
                        List<String> list = ScanUtils.scanJar(src, dest)
                        if (list != null) {
                            proxyClassList.addAll(list)
                        }
                    }
                }
                //将输入文件拷贝到输出目录下
                FileUtils.copyFile(jarInput.file, dest)
            }
        }

        // 3. 处理结果

        println("#########处理结果：")
        proxyClassList.forEach({fileName ->
            println "#####****###########file name = " + fileName
        })
        println("\n########包含AppLifecycleManager类的jar文件")
        println(ScanUtils.FILE_CONTAINS_INIT_CLASS.getAbsolutePath())
        println("########开始自动注册")

        //1.通过前面的步骤，我们已经扫描到所有实现了 IAppLike接口的代理类；
        //2.后面需要在 AppLifeCycleManager 这个类的初始化方法里，动态注入字节码；
        //3.将所有 IAppLike 接口的代理类，通过类名进行反射调用实例化
        //这样最终生成的apk包里，AppLifeCycleManager调用init()方法时，已经可以加载所有组件的生命周期类了
        new AppLifecycleManagerCodeInjector(proxyClassList).execute()

        println "transform finish----------------<<<<<<<\n"
    }
}