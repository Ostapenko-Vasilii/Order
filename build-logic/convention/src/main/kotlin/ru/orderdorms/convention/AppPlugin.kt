package ru.orderdorms.convention

import ru.orderdorms.convention.utils.getLibraryFromLibsToml
import ru.orderdorms.convention.utils.getVersionFromLibsToml
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AppPlugin : Plugin<Project> {
    @OptIn(ExperimentalWasmDsl::class, ExperimentalKotlinGradlePluginApi::class)
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.kotlin.multiplatform.library")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                listOf(
                    iosX64(),
                    iosArm64(),
                    iosSimulatorArm64()
                ).forEach { iosTarget ->
                    iosTarget.binaries.framework {
                        baseName = "ComposeApp"
                        isStatic = true
                    }
                }

                js {
                    browser()
                    binaries.executable()
                }

                wasmJs {
                    browser()
                    binaries.executable()
                }

                sourceSets.apply {
                    val androidMain = getByName("androidMain")
                    val commonMain = getByName("commonMain")

                    androidMain.dependencies {
                        api(getLibraryFromLibsToml("androidx-activity-compose"))
                        implementation(getLibraryFromLibsToml("androidx-activity-compose"))
                        implementation(getLibraryFromLibsToml("androidx-lifecycle-viewmodelCompose"))
                        implementation(getLibraryFromLibsToml("androidx-lifecycle-runtimeCompose"))
                    }
                    commonMain.dependencies {
                        implementation(getLibraryFromLibsToml("compose-runtime"))
                        implementation(getLibraryFromLibsToml("compose-foundation"))
                        implementation(getLibraryFromLibsToml("compose-material3"))
                        implementation(getLibraryFromLibsToml("compose-ui"))
                        implementation(getLibraryFromLibsToml("compose-components-resources"))
                        implementation(getLibraryFromLibsToml("compose-uiToolingPreview"))
                    }
                }

                (this as org.gradle.api.plugins.ExtensionAware).extensions.configure<KotlinMultiplatformAndroidLibraryExtension>("androidLibrary") {
                    namespace = "ru.orderdorms.composeapp"

                    compileSdk = getVersionFromLibsToml("android-compileSdk").toInt()
                    minSdk = getVersionFromLibsToml("android-minSdk").toInt()

                    packaging {
                        resources {
                            excludes += "/META-INF/{AL2.0,LGPL2.1}"
                        }
                    }
                }
            }
        }
    }
}
