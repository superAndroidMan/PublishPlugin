package com.ysjk.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class ReinforceAppPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("reinforceAppInfo", ReinforceAppInfoExtension.class)
        project.tasks.create("reinforceApp", ReinforceAppTask.class)

    }
}