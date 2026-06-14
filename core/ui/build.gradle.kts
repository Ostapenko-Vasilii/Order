plugins {
    id("ru.orderdorms.convention.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.markdown.renderer)
            implementation(libs.markdown.renderer.m3)
            implementation(libs.markdown.renderer.coil3)
        }
    }
}

compose.resources {
    publicResClass = true
}
