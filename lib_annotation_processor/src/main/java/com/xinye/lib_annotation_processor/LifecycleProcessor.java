package com.xinye.lib_annotation_processor;

import com.google.auto.service.AutoService;
import com.xinye.lib_annotation.AppLifecycle;
import com.xinye.lib_annotation.LifecycleConfig;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.HashSet;
import java.util.Set;

@AutoService(Processor.class)
public class LifecycleProcessor extends AbstractProcessor {

    private Elements mElementUtils;

    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        mElementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(AppLifecycle.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}
