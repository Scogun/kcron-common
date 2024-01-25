allprojects {

    group = "com.ucasoft.kcron"

    version = "0.10.1"

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        reports {
            junitXml.required.set(true)
        }
    }
}