plugins {
    alias(libs.plugins.multiplatform) apply false
    id("publish") apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.benchmark) apply false
}

allprojects {

    group = "com.ucasoft.kcron"

    version = "0.23.0"

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        reports {
            junitXml.required.set(true)
        }
    }
}