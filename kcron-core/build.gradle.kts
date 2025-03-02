plugins {
    alias(libs.plugins.multiplatform)
    id("publish")
    alias(libs.plugins.kover)
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
                api(project(":kcron-abstractions"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(project(":kcron-kotlinx-datetime"))
                implementation(libs.kotest.assetions)
            }
        }
    }
}

libraryData {
    name.set("KCron Core")
    description.set("Cron realization for Kotlin Multiplatform")
}