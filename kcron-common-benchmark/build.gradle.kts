plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.benchmark)
}

kotlin {
    jvmToolchain(8)
    jvm()
    linuxX64()
    mingwX64()
    macosX64()
    js(IR) {
        browser()
        nodejs()
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":kcron-common"))
                implementation(libs.kotlinx.benchmark)
            }
        }
    }
}

benchmark {
    targets {
        register("jvm")
        register("js")
        register("linuxX64")
        register("mingwX64")
        register("macosX64")
    }
}