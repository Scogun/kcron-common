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
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    sourceSets {
        commonMain
    }
}

libraryData {
    name.set("KCron Abstractions")
    description.set("Abstractions for Kotlin Multiplatform Cron realization")
}