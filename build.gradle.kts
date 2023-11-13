plugins {
    kotlin("multiplatform") version "1.9.20"
    id("maven-publish")
    id("signing")
}

group = "com.ucasoft.kcron"
version = "0.7.3"

repositories {
    mavenCentral()
}
kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
    linuxX64()
    mingwX64()
    macosX64()
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
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

val stubSources by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
}

val stubJavadoc by tasks.creating(Jar::class) {
    archiveClassifier.set("javadoc")
}

val sourceJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.commonMain.get().kotlin)
}

publishing {
    publications.configureEach {
        if (this is MavenPublication) {
            if (name != "kotlinMultiplatform") {
                artifact(stubJavadoc)
            }
            pom {
                name.set("KCron Common")
                description.set("Cron realization for Kotlin Multiplatform")
                url.set("https://github.com/Scogun/kcron-common")
                licenses {
                    license {
                        name.set("GPL-3.0 License")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.en.html")
                    }
                }
                developers {
                    developer {
                        id.set("Scogun")
                        name.set("Sergey Antonov")
                        email.set("SAntonov@ucasoft.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/Scogun/kcron-common.git")
                    developerConnection.set("scm:git:ssh://github.com:Scogun/kcron-common.git")
                    url.set("https://github.com/Scogun/kcron-common")
                }
            }
        }
    }
    repositories {
        maven {
            name = "MavenCentral"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
}

signing {
    sign(publishing.publications)
}

tasks.withType<AbstractPublishToMaven>().configureEach {
    val signingTasks = tasks.withType<Sign>()
    mustRunAfter(signingTasks)
}