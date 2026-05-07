package ru.orderdorms.convention

import ru.orderdorms.convention.utils.getLibraryFromLibsToml
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class FeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply("ru.orderdorms.convention.multiplatform")
                apply("ru.orderdorms.convention.ui")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.apply {
                    val androidMain = getByName("androidMain")
                    val commonMain = getByName("commonMain")

                    commonMain.dependencies {
                        implementation(getLibraryFromLibsToml("compose-foundation"))
                        implementation(getLibraryFromLibsToml("coroutines-core"))
                    }
                    androidMain.dependencies {
                        implementation(getLibraryFromLibsToml("coroutines-android"))
                        implementation(getLibraryFromLibsToml("androidx-lifecycle-viewmodelCompose"))
                        implementation(getLibraryFromLibsToml("androidx-lifecycle-runtimeCompose"))
                    }
                }
            }
        }
    }
}
