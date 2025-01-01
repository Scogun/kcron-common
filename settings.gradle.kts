plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

rootProject.name = "kcron"

include(
    "kcron-abstractions",
    "kcron-common",
    "kcron-common-benchmark",
    "kcron-core",
    "kcron-kotlinx-datetime"
)