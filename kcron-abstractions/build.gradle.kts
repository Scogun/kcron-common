plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.maven.publish)
}

kotlin {
    jvmToolchain(8)
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
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    sourceSets {
        commonMain
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    pom {
        configurePom("KCron Abstractions", "Abstractions for Kotlin Multiplatform Cron realization", this)
    }
}