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
        commonMain {
            dependencies {
                api(project(":kcron-core"))
                api(project(":kcron-kotlinx-datetime"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotest.assetions)
            }
        }
    }
}

libraryData {
    name.set("KCron Common")
    description.set("Cron realization for Kotlin Multiplatform with Kotlinx DateTime Provider")
}