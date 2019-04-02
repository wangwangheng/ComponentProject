package com.xinye.plugin_lifecycle

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.AppExtension

class LifecyclePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("################Lifecycle插件开始##############")

        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(new LifecycleTransform(project))
        println("################Lifecycle插件完成##############")
    }
}