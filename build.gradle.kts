allprojects {

    group = "com.ucasoft.kcron"

    version = "0.14.4"

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        reports {
            junitXml.required.set(true)
        }
    }
}