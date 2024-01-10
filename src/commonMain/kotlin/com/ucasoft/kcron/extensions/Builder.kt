package com.ucasoft.kcron.extensions

import com.ucasoft.kcron.builders.Builder
import com.ucasoft.kcron.builders.CronDateTime
import com.ucasoft.kcron.common.*

fun <T, C: CronDateTime<T>> Builder<T, C>.anySeconds() : Builder<T, C> {
    return seconds(TimeGroups.Any, "*")
}

fun <T, C: CronDateTime<T>> Builder<T, C>.seconds(vararg values: Int) : Builder<T, C> {
    return seconds(TimeGroups.Specific, values.joinToString(","))
}

fun <T, C: CronDateTime<T>> Builder<T, C>.seconds(at: At) : Builder<T, C> {
    return seconds(TimeGroups.EveryStartingAt, at.toString())
}

fun <T, C: CronDateTime<T>> Builder<T, C>.seconds(between: IntRange) : Builder<T, C> {
    return seconds(TimeGroups.EveryBetween, "${between.first}-${between.last}")
}

fun <T, C: CronDateTime<T>> Builder<T, C>.anyMinutes() : Builder<T, C> {
    return minutes(TimeGroups.Any, "*")
}

fun <T, C: CronDateTime<T>> Builder<T, C>.minutes(vararg values: Int) : Builder<T, C> {
    return minutes(TimeGroups.Specific, values.joinToString(","))
}

fun <T, C: CronDateTime<T>> Builder<T, C>.minutes(at: At) : Builder<T, C> {
    return minutes(TimeGroups.EveryStartingAt, at.toString())
}

fun <T, C: CronDateTime<T>> Builder<T, C>.minutes(between: IntRange) : Builder<T, C> {
    return minutes(TimeGroups.EveryBetween, "${between.first}-${between.last}")
}

fun <T, C: CronDateTime<T>> Builder<T, C>.anyHours() : Builder<T, C> {
    return hours(TimeGroups.Any, "*")
}

fun <T, C: CronDateTime<T>> Builder<T, C>.hours(vararg values: Int) : Builder<T, C> {
    return hours(TimeGroups.Specific, values.joinToString(","))
}

fun <T, C: CronDateTime<T>> Builder<T, C>.hours(at: At) : Builder<T, C> {
    return hours(TimeGroups.EveryStartingAt, at.toString())
}

fun <T, C: CronDateTime<T>> Builder<T, C>.hours(between: IntRange) : Builder<T, C> {
    return hours(TimeGroups.EveryBetween, "${between.first}-${between.last}")
}

fun <T, C: CronDateTime<T>> Builder<T, C>.anyDays() : Builder<T, C> {
    return days(DayGroups.Any, "?")
}

fun <T, C: CronDateTime<T>> Builder<T, C>.days(vararg values: Int) : Builder<T, C> {
    return days(DayGroups.Specific, values.joinToString(","))
}

fun <T, C: CronDateTime<T>> Builder<T, C>.days(at: At) : Builder<T, C> {
    return days(DayGroups.EveryStartingAt, at.toString())
}

fun <T, C: CronDateTime<T>> Builder<T, C>.lastDay() : Builder<T, C> {
    return days(DayGroups.LastDay, "L")
}

fun <T, C: CronDateTime<T>> Builder<T, C>.lastWorkDay() : Builder<T, C> {
    return days(DayGroups.LastWeekday, "LW")
}

fun <T, C: CronDateTime<T>> Builder<T, C>.nearestWorkDay(value: Int) : Builder<T, C> {
    return days(DayGroups.NearestWeekday, "${value}W")
}

fun <T, C: CronDateTime<T>> Builder<T, C>.anyMonths() : Builder<T, C> {
    return months(MonthGroups.Any, "*")
}

fun <T, C: CronDateTime<T>> Builder<T, C>.months(vararg values: Int) : Builder<T, C> {
    return months(MonthGroups.Specific, values.joinToString(","))
}

fun <T, C: CronDateTime<T>> Builder<T, C>.months(at: At) : Builder<T, C> {
    return months(MonthGroups.EveryStartingAt, at.toString())
}

fun <T, C: CronDateTime<T>> Builder<T, C>.months(between: IntRange) : Builder<T, C> {
    return months(MonthGroups.EveryBetween, "${between.first}-${between.last}")
}

fun <T, C: CronDateTime<T>> Builder<T, C>.anyDaysOfWeek(any: String = "*") : Builder<T, C> {
    return daysOfWeek(DayOfWeekGroups.Any, any)
}

fun <T, C: CronDateTime<T>> Builder<T, C>.daysOfWeek(vararg values: Int) : Builder<T, C> {
    return daysOfWeek(DayOfWeekGroups.Specific, values.joinToString(",") )
}

fun <T, C: CronDateTime<T>> Builder<T, C>.daysOfWeek(at: At) : Builder<T, C> {
    return daysOfWeek(DayOfWeekGroups.EveryStartingAt, at.toString())
}

fun <T, C: CronDateTime<T>> Builder<T, C>.lastDayOfWeek(value: Int) : Builder<T, C> {
    return daysOfWeek(DayOfWeekGroups.Last, "${value}L")
}

fun <T, C: CronDateTime<T>> Builder<T, C>.daysOfWeek(on: On) : Builder<T, C> {
    return daysOfWeek(DayOfWeekGroups.OfMonth, on.toString())
}

fun <T, C: CronDateTime<T>> Builder<T, C>.anyYears() : Builder<T, C> {
    return years(YearGroups.Any, "*")
}

fun <T, C: CronDateTime<T>> Builder<T, C>.years(vararg values: Int): Builder<T, C> {
    return years(YearGroups.Specific, values.joinToString(","))
}

fun <T, C: CronDateTime<T>> Builder<T, C>.years(at: At) : Builder<T, C> {
    return years(YearGroups.EveryStartingAt, at.toString())
}

fun <T, C: CronDateTime<T>> Builder<T, C>.years(between: IntRange) : Builder<T, C> {
    return years(YearGroups.EveryBetween, "${between.first}-${between.last}")
}