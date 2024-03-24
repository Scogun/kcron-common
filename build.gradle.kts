allprojects {

    group = "com.ucasoft.kcron"

    version = "0.11.0"

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        reports {
            junitXml.required.set(true)
        }
    }
}