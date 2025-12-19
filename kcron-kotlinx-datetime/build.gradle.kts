plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.maven.publish)
}

kotlin {
    jvmToolchain(11)
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

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    pom {
        configurePom("KCron Kotlinx DateTime", "Kotlinx DateTime Provider for Kotlin Multiplatform Cron realization", this)
    }
}