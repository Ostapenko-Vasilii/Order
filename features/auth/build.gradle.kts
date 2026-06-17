plugins {
    id("ru.orderdorms.convention.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":core:ui"))
            implementation(project(":core:data"))
            implementation(project(":core:domain"))
            implementation(libs.kotlinx.serialization)
            implementation(libs.koin.core)
        }
    }
}
compose.resources {
    publicResClass = true
    generateResClass = auto
    packageOfResClass = "ru.orderdorms.features.auth.resources"
}
