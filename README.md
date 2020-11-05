# KCron
Cron realization for Kotlin Multiplatform

##### Features
* Kotlin Multiplatform library
* Parse Cron expressions
  * Include L, W, LW, '?' and #
* Build Cron expression by smart builder functions:
```kotlin
builder
    .seconds(0 at 10) //Every 10 seconds starting at 0 seconds
    .minutes(5..25) // Every minute between 5 and 25
    .hours(5, 12) // Specific hours: 5 and 15
    .daysOfWeek(7 on 5) // On the 5th Saturday of the month
    .years(2050) // Specific year: 2050
```
* Parsing validation includes combination rules
  * 'days' and 'days of week' could not be setup simultaneously
##### Usage
***Build Cron expression***
```kotlin
val builder = KCron.builder()
// By default builder contains any expression for every part
println(builder.expression) // * * * ? * * *
builder
    .seconds(0 at 10)
    .minutes(5..25)
    .hours(5, 12)
    .daysOfWeek(7 on 5)
    .years(2050)
println(builder.expression) // 0/10 5-25 5,12 ? * 7#5 2050
```
***Parse Cron expression***
```kotlin
val builder = KCron.parseAndBuild("0/10 5-25 5,12 ? * 7#5 2050")
println(builder.nextRunList()) // 10 is as default list size
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
```
##### Current status
This library is on alpha version `0.0.9`.
However, it will be a part of another cool library.
Check the news! 
