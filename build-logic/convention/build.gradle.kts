plugins {
    `kotlin-dsl`
}

group = "ru.orderdorms.convention"

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.kotlin.serialization.gradlePlugin)
    implementation(libs.compose.gradlePlugin)
    implementation(libs.compose.compiler.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("app") {
            id = "ru.orderdorms.convention.app"
            implementationClass = "ru.orderdorms.convention.AppPlugin"
        }
        register("androidApp") {
            id = "ru.orderdorms.convention.android.app"
            implementationClass = "ru.orderdorms.convention.AndroidApplicationPlugin"
        }
        register("multiplatform") {
            id = "ru.orderdorms.convention.multiplatform"
            implementationClass = "ru.orderdorms.convention.MultiplatformPlugin"
        }
        register("ui") {
            id = "ru.orderdorms.convention.ui"
            implementationClass = "ru.orderdorms.convention.UiPlugin"
        }
        register("feature") {
            id = "ru.orderdorms.convention.feature"
            implementationClass = "ru.orderdorms.convention.FeaturePlugin"
        }
    }
}
