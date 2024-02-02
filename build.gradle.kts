allprojects {

    group = "com.ucasoft.kcron"

    version = "0.10.2"

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        reports {
            junitXml.required.set(true)
        }
    }
}