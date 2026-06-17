plugins {
    id("ru.orderdorms.convention.app")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":core:ui"))
            implementation(project(":core:data"))
            implementation(project(":core:domain"))
            implementation(project(":features:auth"))
            implementation(project(":features:home"))
            implementation(project(":features:services"))
            implementation(project(":features:events"))
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

compose.resources {
    publicResClass = true
    generateResClass = auto
    packageOfResClass = "ru.orderdorms.composeapp.resources"
}

