allprojects {

    group = "com.ucasoft.kcron"

    version = "0.13.2"

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        reports {
            junitXml.required.set(true)
        }
    }
}