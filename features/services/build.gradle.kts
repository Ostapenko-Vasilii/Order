plugins {
    id("ru.orderdorms.convention.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:ui"))
            implementation(project(":core:domain"))
            implementation(project(":core:data"))
            implementation(libs.kotlinx.serialization)
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
            implementation(libs.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.core)
            implementation(libs.multiplatform.settings)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.markdown.renderer)
            implementation(libs.markdown.renderer.m3)
        }
    }
}

compose.resources {
    publicResClass = false
    generateResClass = auto
}



