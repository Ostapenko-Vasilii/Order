plugins {
    id("ru.orderdorms.convention.app")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:ui"))
            implementation(project(":core:data"))
            implementation(project(":core:domain"))
            implementation(project(":features:auth"))
            implementation(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.compose.uiTooling)
        }
    }
}

