plugins {
    alias(libs.plugins.multiplatform) apply false
    alias(libs.plugins.maven.publish) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.benchmark) apply false
}

allprojects {

    group = "com.ucasoft.kcron"

    repositories {
        google()
        mavenCentral()
    }
    version = "0.31.6"

    tasks.withType<Test> {
        reports {
            junitXml.required.set(true)
        }
    }
}