package com.ucasoft.kcron.core.common

enum class CronPart(val partName: String) {

    Seconds("seconds"),
    Minutes("minutes"),
    Hours("hours"),
    Days("days"),
    Months("months"),
    DaysOfWeek("days of the week"),
    Years("years")
}