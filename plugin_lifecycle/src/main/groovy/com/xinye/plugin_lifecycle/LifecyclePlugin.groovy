package com.xinye.plugin_lifecycle

import org.gradle.api.Plugin
import org.gradle.api.Project

class LifecyclePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("################Hello world##############")
    }
}