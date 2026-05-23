plugins {
    id("ru.orderdorms.convention.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:ui"))
            implementation(project(":core:domain"))
            implementation(project(":features:services"))
            implementation(libs.kotlinx.serialization)
            implementation(libs.koin.core)
            implementation(libs.multiplatform.settings)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
        }
    }
}

compose.resources {
    publicResClass = false
    generateResClass = auto
}

