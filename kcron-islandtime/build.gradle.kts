plugins {
    alias(libs.plugins.multiplatform)
    id("publish")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }
    macosX64()
    macosArm64()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":kcron-core"))
                implementation(libs.islandtime)
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
    name.set("KCron Island Time")
    description.set("Cron realization for Kotlin Multiplatform with Island Time Provider")
}