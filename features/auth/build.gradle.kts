plugins {
    id("ru.orderdorms.convention.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:ui"))
            implementation(libs.multiplatformSettings)
            implementation(libs.kotlinx.serialization)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
        }
    }
}
compose.resources {
    publicResClass = false
    generateResClass = auto
}
