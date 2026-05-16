plugins {
    id("ru.orderdorms.convention.android.app")
}

dependencies {
    implementation(project(":composeApp"))
}
android {
    compileSdk {
        version = release(37)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildToolsVersion = "37.0.0"
}
