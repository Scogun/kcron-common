package com.ucasoft.kcron.extensions

import com.ucasoft.kcron.builders.Builder
import com.ucasoft.kcron.common.*

fun Builder.anySeconds() : Builder {
    return seconds(TimeGroups.Any, "*")
}

fun Builder.seconds(vararg values: Int) : Builder {
    return seconds(TimeGroups.Specific, values.joinToString(","))
}

fun Builder.seconds(between: IntRange) : Builder {
    return seconds(TimeGroups.EveryBetween, "${between.first}-${between.last}")
}

fun Builder.anyMinutes() : Builder {
    return minutes(TimeGroups.Any, "*")
}

fun Builder.minutes(vararg values: Int) : Builder {
    return minutes(TimeGroups.Specific, values.joinToString(","))
}

fun Builder.minutes(between: IntRange) : Builder {
    return minutes(TimeGroups.EveryBetween, "${between.first}-${between.last}")
}

fun Builder.anyHours() : Builder {
    return hours(TimeGroups.Any, "*")
}

fun Builder.hours(vararg values: Int) : Builder {
    return hours(TimeGroups.Specific, values.joinToString(","))
}

fun Builder.hours(between: IntRange) : Builder {
    return hours(TimeGroups.EveryBetween, "${between.first}-${between.last}")
}

fun Builder.anyDays() : Builder {
    return days(DayGroups.Any, "*")
}

fun Builder.days(vararg values: Int) : Builder {
    return days(DayGroups.Specific, values.joinToString(","))
}

fun Builder.lastDay() : Builder {
    return days(DayGroups.LastDay, "L")
}

fun Builder.lastWorkDay() : Builder {
    return days(DayGroups.LastWeekday, "LW")
}

fun Builder.nearestWorkDay(value: Int) : Builder {
    return days(DayGroups.NearestWeekday, "${value}W")
}

fun Builder.anyMonths() : Builder {
    return months(MonthGroups.Any, "*")
}

fun Builder.months(vararg values: Int) : Builder {
    return months(MonthGroups.Specific, values.joinToString(","))
}

fun Builder.months(between: IntRange) : Builder {
    return months(MonthGroups.EveryBetween, "${between.first}-${between.last}")
}

fun Builder.anyDaysOfWeek() : Builder {
    return daysOfWeek(DayOfWeekGroups.Any, "*")
}

fun Builder.daysOfWeek(vararg values: Int) : Builder {
    return daysOfWeek(DayOfWeekGroups.Specific, values.joinToString(",") )
}

fun Builder.anyYears() : Builder {
    return years(YearGroups.Any, "*")
}

fun Builder.years(vararg values: Int): Builder {
    return years(YearGroups.Specific, values.joinToString(","))
}

fun Builder.years(between: IntRange) : Builder {
    return years(YearGroups.EveryBetween, "${between.first}-${between.last}")
}