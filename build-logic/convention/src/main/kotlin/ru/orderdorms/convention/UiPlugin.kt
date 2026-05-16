package ru.orderdorms.convention

import ru.orderdorms.convention.utils.getLibraryFromLibsToml
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class UiPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply("ru.orderdorms.convention.multiplatform")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.apply {
                    val androidMain = getByName("androidMain")
                    val commonMain = getByName("commonMain")

                    androidMain.dependencies {
                        implementation(getLibraryFromLibsToml("compose-uiTooling"))
                        implementation(getLibraryFromLibsToml("compose-uiToolingPreview"))
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
                        implementation(getLibraryFromLibsToml("coil"))
                        implementation(getLibraryFromLibsToml("coil-network-ktor"))
                    }
                }
            }
        }
    }
}
