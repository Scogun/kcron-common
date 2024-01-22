package com.ucasoft.kcron.kotlinx.datetime

import kotlinx.datetime.*

fun LocalDateTime.toCronLocalDateTime() =
    CronLocalDateTime(this.year, this.monthNumber, this.dayOfMonth, this.hour, this.minute, this.second)

fun LocalDateTime.plusHours(hours: Int, timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return plus(this, hours, DateTimeUnit.HOUR, timeZone)
}

fun LocalDateTime.plusDays(days: Int, timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return plus(this, days, DateTimeUnit.DAY, timeZone)
}

fun LocalDateTime.minusDays(days: Int, timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return plus(this, if (days > 0) { days * -1 } else { days }, DateTimeUnit.DAY, timeZone)
}

private fun plus(self: LocalDateTime, value: Int, unit: DateTimeUnit, timeZone: TimeZone) : LocalDateTime {
    return self.toInstant(timeZone).plus(value, unit, timeZone).toLocalDateTime(timeZone)
}