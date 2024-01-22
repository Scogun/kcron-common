plugins {
    alias(libs.plugins.multiplatform)
    id("publish")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
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