plugins {
    id("ru.orderdorms.convention.multiplatform")
    alias(libs.plugins.buildKonfig)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:domain"))
            implementation(libs.kotlinx.serialization)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.koin.core)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.no.arg)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }

        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

buildkonfig {
    packageName = "ru.orderdorms.core.data"

    defaultConfigs {
        buildConfigField(
            type = com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            name = "BASE_URL",
            value = providers.gradleProperty("backend.base.url").orElse("http://localhost:8000").get()
        )
    }
}

