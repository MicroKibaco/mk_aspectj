package com.github.microkibaco
import org.gradle.api.Plugin
import org.gradle.api.Project

public class MkPluginConfig implements Plugin<Project>{
    boolean debug;

    @Override
    void apply(Project project) {

        print("This is my first PUGIN")

        project.task('My-Plugin-Task') << {
            print("This is my first PUGIN")
        }

        project.afterEvaluate {

            print("debug=" + project.sensorsData.debug)

        }

    }
}