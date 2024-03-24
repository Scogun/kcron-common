plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "kcron"

include(
    "kcron-abstractions",
    "kcron-common",
    "kcron-core",
    "kcron-islandtime",
    "kcron-kotlinx-datetime"
)