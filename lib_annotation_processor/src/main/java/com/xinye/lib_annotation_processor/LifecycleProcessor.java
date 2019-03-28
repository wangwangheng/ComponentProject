package com.xinye.lib_annotation_processor;

import com.google.auto.service.AutoService;
import com.xinye.lib_annotation.AppLifecycle;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Writer;
import java.util.*;

@AutoService(Processor.class)
public class LifecycleProcessor extends AbstractProcessor {

    private Elements mElementUtils;
    private Map<String, LifecycleProxyGenerator> mMap = new HashMap<>();
    private Messager mMessager;
    private Filer mFiler;

    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtils = processingEnvironment.getElementUtils();
        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(AppLifecycle.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements  = roundEnvironment.getElementsAnnotatedWith(AppLifecycle.class);
        mMap.clear();
        for(Element element : elements){
            if(!element.getKind().isClass()){
                throw new RuntimeException(AppLifecycle.class.getSimpleName() + "注解只能用在类上");
            }

            // 强制转换为TypeElement，也就是类元素，可以获取使用该注解的类的相关信息
            TypeElement typeElement = (TypeElement) element;

            // use
            List<? extends TypeMirror> interfaces = typeElement.getInterfaces();
            if(interfaces == null || interfaces.size() == 0){
                throw new RuntimeException("use" + AppLifecycle.class.getSimpleName() + ",but not implements "
                        + typeElement.getSimpleName().toString());
            }

            boolean checkInterfaceFlag = false;
            for (TypeMirror mirror : interfaces) {
                if ("com.xinye.core.IAppLifecycle".equals(mirror.toString())) {
                    checkInterfaceFlag = true;
                    break;
                }
            }
            if (!checkInterfaceFlag) {
                throw new RuntimeException(typeElement.getQualifiedName() + "必须实现com.xinye.core.IAppLifecycle接口");
            }

            //该类的全限定类名
            String fullClassName = typeElement.getQualifiedName().toString();
            if (!mMap.containsKey(fullClassName)) {
                mMessager.printMessage(Diagnostic.Kind.NOTE,"处理类AA: " + fullClassName);
                //创建代理类生成器
                LifecycleProxyGenerator creator = new LifecycleProxyGenerator(mElementUtils, typeElement);
                mMap.put(fullClassName, creator);
            }
        }

        mMessager.printMessage(Diagnostic.Kind.NOTE,"···start generator proxy class");
        for(Map.Entry<String, LifecycleProxyGenerator> entry : mMap.entrySet()){
            String className = entry.getKey();
            LifecycleProxyGenerator creator = entry.getValue();
            mMessager.printMessage(Diagnostic.Kind.NOTE,"generator proxy class:" + className);

            //  generate proxy class
            try {
                JavaFileObject jfo = mFiler.createSourceFile(creator.getProxyClassFullName());
                Writer writer = jfo.openWriter();
                writer.write(creator.generatorClass());
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}