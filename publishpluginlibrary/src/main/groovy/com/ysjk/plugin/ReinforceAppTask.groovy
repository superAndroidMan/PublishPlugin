package com.ysjk.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ReinforceAppTask extends DefaultTask {
    ReinforceAppTask() {
        group = 'ysjk'
        description = 'reinforce app'
        dependsOn("build")
    }

    @TaskAction
    void doAction() {
        def oldApkPath = project.extensions.reinforceAppInfo.apkPath
        def qhJar = project.extensions.reinforceAppInfo.qihuPath
        def keyStorePath = project.extensions.reinforceAppInfo.keyStorePath
        def keyStorePass = project.extensions.reinforceAppInfo.keyStorePass
        def keyStoreKeyAlias = project.extensions.reinforceAppInfo.keyStoreKeyAlias
        def keyStoreKeyAliasPass = project.extensions.reinforceAppInfo.keyStoreKeyAliasPass
        execCmd("java -jar ${qhJar} -login 360账号 密码")
        execCmd("java -jar ${qhJar}  -importsign ${keyStorePath} ${keyStorePass} ${keyStoreKeyAlias} ${keyStoreKeyAliasPass}")
        execCmd("java -jar ${qhJar} -importmulpkg ${project.extensions.reinforceAppInfo.channelPath}")
        def outputPath = project.extensions.reinforceAppInfo.outputPath
        if (!outputPath.endsWith("/")) {
            outputPath += "/"
        }
        def projectName = project.extensions.reinforceAppInfo.projectName
        def projectVersion = project.extensions.reinforceAppInfo.projectVersion
        def apkOutputDir = outputPath
        if (projectName){
            apkOutputDir += projectName
        }
        if (projectVersion){
            apkOutputDir += projectVersion
        }
        def tmp = new File(apkOutputDir)
        if (!tmp.exists()) {
            tmp.mkdirs()
        }
        execCmd("java -jar ${qhJar} -jiagu ${oldApkPath} ${apkOutputDir} -autosign  -automulpkg")
    }

    void execCmd(cmd) {
        project.exec {
            executable 'bash'
            args '-c', cmd
        }
    }
}
