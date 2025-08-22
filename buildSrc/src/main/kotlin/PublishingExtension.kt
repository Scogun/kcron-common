import org.gradle.api.publish.maven.MavenPom

fun configurePom(name: String, description: String, pom: MavenPom) {
    pom.name.set(name)
    pom.description.set(description)
    pom.url.set("https://github.com/Scogun/kcron-common")
    pom.licenses {
        license {
            this.name.set("The Apache License, Version 2.0")
            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
        }
    }
    pom.developers {
        developer {
            id.set("Scogun")
            this.name.set("Sergey Antonov")
            email.set("SAntonov@ucasoft.com")
        }
        developer {
            id.set("Myshkouski")
            this.name.set("Alexei Myshkouski")
            email.set("alexeimyshkouski@gmail.com")
        }
    }
    pom.scm {
        connection.set("scm:git:git://github.com/Scogun/kcron-common.git")
        developerConnection.set("scm:git:ssh://github.com:Scogun/kcron-common.git")
        url.set("https://github.com/Scogun/kcron-common")
    }
}