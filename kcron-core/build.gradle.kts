plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.kover)
}

kotlin {
    jvmToolchain(11)
    jvm()
    linuxX64()
    linuxArm64()
    mingwX64()
    macosArm64()
    js {
        browser()
        nodejs()
    }
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        nodejs()
    }
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

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    pom {
        configurePom("KCron Core", "Cron realization for Kotlin Multiplatform", this)
    }
}