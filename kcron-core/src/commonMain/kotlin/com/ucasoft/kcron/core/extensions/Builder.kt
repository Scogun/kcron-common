package com.ucasoft.kcron.core.extensions

import com.ucasoft.kcron.abstractions.CronDateTime
import com.ucasoft.kcron.abstractions.CronDateTimeProvider
import com.ucasoft.kcron.core.builders.Builder
import com.ucasoft.kcron.core.common.*

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.anySeconds() : Builder<T, D, P> {
    return seconds(TimeGroups.Any, "*")
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.seconds(vararg values: Int) : Builder<T, D, P> {
    return seconds(TimeGroups.Specific, values.joinToString(","))
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.seconds(at: At) : Builder<T, D, P> {
    return seconds(TimeGroups.EveryStartingAt, at.toString())
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.seconds(between: IntRange) : Builder<T, D, P> {
    return seconds(TimeGroups.EveryBetween, "${between.first}-${between.last}")
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.anyMinutes() : Builder<T, D, P> {
    return minutes(TimeGroups.Any, "*")
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.minutes(vararg values: Int) : Builder<T, D, P> {
    return minutes(TimeGroups.Specific, values.joinToString(","))
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.minutes(at: At) : Builder<T, D, P> {
    return minutes(TimeGroups.EveryStartingAt, at.toString())
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.minutes(between: IntRange) : Builder<T, D, P> {
    return minutes(TimeGroups.EveryBetween, "${between.first}-${between.last}")
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.anyHours() : Builder<T, D, P> {
    return hours(TimeGroups.Any, "*")
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.hours(vararg values: Int) : Builder<T, D, P> {
    return hours(TimeGroups.Specific, values.joinToString(","))
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.hours(at: At) : Builder<T, D, P> {
    return hours(TimeGroups.EveryStartingAt, at.toString())
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.hours(between: IntRange) : Builder<T, D, P> {
    return hours(TimeGroups.EveryBetween, "${between.first}-${between.last}")
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.anyDays() : Builder<T, D, P> {
    return days(DayGroups.Any, "?")
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.days(vararg values: Int) : Builder<T, D, P> {
    return days(DayGroups.Specific, values.joinToString(","))
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.days(at: At) : Builder<T, D, P> {
    return days(DayGroups.EveryStartingAt, at.toString())
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.lastDay() : Builder<T, D, P> {
    return days(DayGroups.LastDay, "L")
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.lastWorkDay() : Builder<T, D, P> {
    return days(DayGroups.LastWeekday, "LW")
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.nearestWorkDay(value: Int) : Builder<T, D, P> {
    return days(DayGroups.NearestWeekday, "${value}W")
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.anyMonths() : Builder<T, D, P> {
    return months(MonthGroups.Any, "*")
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.months(vararg values: Int) : Builder<T, D, P> {
    return months(MonthGroups.Specific, values.joinToString(","))
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.months(at: At) : Builder<T, D, P> {
    return months(MonthGroups.EveryStartingAt, at.toString())
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.months(between: IntRange) : Builder<T, D, P> {
    return months(MonthGroups.EveryBetween, "${between.first}-${between.last}")
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.anyDaysOfWeek(any: String = "*") : Builder<T, D, P> {
    return daysOfWeek(DayOfWeekGroups.Any, any)
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.daysOfWeek(vararg values: Int) : Builder<T, D, P> {
    return daysOfWeek(DayOfWeekGroups.Specific, values.joinToString(",") )
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.daysOfWeek(at: At) : Builder<T, D, P> {
    return daysOfWeek(DayOfWeekGroups.EveryStartingAt, at.toString())
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.lastDayOfWeek(value: Int) : Builder<T, D, P> {
    return daysOfWeek(DayOfWeekGroups.Last, "${value}L")
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.daysOfWeek(on: On) : Builder<T, D, P> {
    return daysOfWeek(DayOfWeekGroups.OfMonth, on.toString())
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.anyYears() : Builder<T, D, P> {
    return years(YearGroups.Any, "*")
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.years(vararg values: Int): Builder<T, D, P> {
    return years(YearGroups.Specific, values.joinToString(","))
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.years(at: At) : Builder<T, D, P> {
    return years(YearGroups.EveryStartingAt, at.toString())
}

fun <T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>> Builder<T, D, P>.years(between: IntRange) : Builder<T, D, P> {
    return years(YearGroups.EveryBetween, "${between.first}-${between.last}")
}