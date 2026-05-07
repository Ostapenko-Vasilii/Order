package ru.orderdorms.convention

import ru.orderdorms.convention.utils.getVersionFromLibsToml
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<ApplicationExtension> {
                namespace = "ru.orderdorms"

                compileSdk = getVersionFromLibsToml("android-compileSdk").toInt()

                defaultConfig {
                    applicationId = "ru.orderdorms"
                    minSdk = getVersionFromLibsToml("android-minSdk").toInt()
                    targetSdk = getVersionFromLibsToml("android-targetSdk").toInt()
                    versionCode = 1
                    versionName = "1.0.0"
                }
                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                    }
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
            }
        }
    }
}
