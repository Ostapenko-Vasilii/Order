plugins {
    id("ru.orderdorms.convention.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:ui"))
            implementation(libs.kotlinx.serialization)
        }
    }
}
compose.resources {
    publicResClass = false
    generateResClass = auto
}
