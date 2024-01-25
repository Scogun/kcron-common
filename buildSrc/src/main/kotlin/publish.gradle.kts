plugins {
    `maven-publish`
    signing
}

val libraryData = extensions.create("libraryData", PublishingExtension::class)

val stubJavadoc by tasks.creating(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications.configureEach  {
        if (this is MavenPublication) {
            if (name != "kotlinMultiplatform") {
                artifact(stubJavadoc)
            }
            pom {
                name.set(libraryData.name)
                description.set(libraryData.description)
                url.set("https://github.com/Scogun/kcron-common")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("Scogun")
                        name.set("Sergey Antonov")
                        email.set("SAntonov@ucasoft.com")
                    }
                    developer {
                        id.set("Myshkouski")
                        name.set("Alexei Myshkouski")
                        email.set("alexeimyshkouski@gmail.com")
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