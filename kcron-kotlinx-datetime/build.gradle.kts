plugins {
    alias(libs.plugins.multiplatform)
    id("publish")
}

kotlin {
    jvmToolchain(8)
    jvm()
    linuxX64()
    linuxArm64()
    mingwX64()
    macosX64()
    macosArm64()
    js(IR) {
        browser()
        nodejs()
    }
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        nodejs()
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":kcron-abstractions"))
                api(libs.kotlinx.datetime)
            }
        }
    }
}

libraryData {
    name.set("KCron Kotlinx DateTime")
    description.set("Kotlinx DateTime Provider for Kotlin Multiplatform Cron realization")
}