# KCron Common
Cron realization for Kotlin Multiplatform

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Scogun_kcron-common&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Scogun_kcron-common) ![GitHub](https://img.shields.io/github/license/Scogun/kcron-common?color=blue) ![Publish workflow](https://github.com/Scogun/kcron-common/actions/workflows/publish.yml/badge.svg) [![Maven Central with version prefix filter](https://img.shields.io/maven-central/v/com.ucasoft.kcron/kcron-common/0.8.0?color=blue)](https://search.maven.org/artifact/com.ucasoft.kcron/kcron-common/0.8.0/jar)

### Features
* Kotlin Multiplatform library
* Parse Cron expressions
  * Include L, W, LW, '?' and #
* Build Cron expression by smart builder functions:
```kotlin
builder
    .seconds(10 at 0) //Every 10 seconds starting at 0 seconds
    .minutes(5..25) // Every minute between 5 and 25
    .hours(5, 12) // Specific hours: 5 and 12
    .daysOfWeek(7 on 5) // On the 5th Sunday of the month
    .years(2050) // Specific year: 2050
```
* Support custom first week day
```kotlin
val builder = Builder(WeekDays.Sunday)
builder
    .daysOfWeek(7 on 5) // On the 5th Saturday of the month
```
* Parsing validation includes combination rules
  * 'days' and 'days of week' could not be setup simultaneously
* Support
  * JVM
  * Linux
  * Windows (mingwX64)
  * macOS
  * Javascript
  * iOS
### Usage
***Add with Gradle***
```groovy
kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation 'com.ucasoft.kcron:kcron-common:0.8.0'
            }
        }
    }
}
```
***Build Cron expression***
```kotlin
val builder = KCron.builder()
// By default, builder contains any expression for every part
println(builder.expression) // * * * ? * * *
builder
    .seconds(10 at 0)
    .minutes(5..25)
    .hours(5, 12)
    .daysOfWeek(7 on 5)
    .years(2050)
println(builder.expression) // 0/10 5-25 5,12 ? * 7#5 2050
```
***Parse as Classic as well as Modern Cron expressions***
```kotlin
// Auto detect
val builder = KCron.parseAndBuild("0/10 5-25 5,12 ? * 7#5 2050") {
    it.firstDayOfWeek = WeekDays.Sunday
}
@OptIn(DelicateIterableApi::class)
println(builder.asIterable().take(10))
/* Result:
[
    2050-01-29T05:05,
    2050-01-29T05:05:10,
    2050-01-29T05:05:20,
    2050-01-29T05:05:30,
    2050-01-29T05:05:40,
    2050-01-29T05:05:50,
    2050-01-29T05:06,
    2050-01-29T05:06:10,
    2050-01-29T05:06:20,
    2050-01-29T05:06:30
]
*/
// OR Force to parse only Classic expressions
try {
    val builder = KCron.parseAndBuild("0/10 5-25 5,12 ? * 7#5 2050") {
        it.version = Version.Classic
    }
} catch(e: WrongCronExpression) {
    println(e.message) // Expression 0/10 5-25 5,12 ? * 7#5 2050 is not Classic Cron one!
}
```
***Days of week and months can be defined in a parsed expression as numbers as well as short names***
```kotlin
builder = KCron.parseAndBuild("15/10 5-25 5 ? JAN,MAR 2,3,4,5 2050")
println(builder.nextRun) // 2050-01-04T05:05:15
// OR
builder = KCron.parseAndBuild("15/10 5-25 5 ? 2-4 MON 2050")
println(builder.nextRun) // 2050-02-07T05:05:15
```
***Easy change any part of expression***
```kotlin
val builder = KCron.parseAndBuild("0/10 5-25 5,12 ? * SUN#5 2050")
builder.years(2021..2025)
println(builder.expression) // 0/10 5-25 5,12 ? * SUN#5 2021-2025
``` 
### Current status
This library is on beta version `0.8.0`.
However, it will be a part of another cool library.
Check the news! 
