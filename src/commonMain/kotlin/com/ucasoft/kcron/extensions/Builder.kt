package com.ucasoft.kcron.extensions

import com.ucasoft.kcron.builders.Builder
import com.ucasoft.kcron.common.*

fun Builder.anySeconds() : Builder {
    return seconds(TimeGroups.Any, "*")
}

fun Builder.seconds(value: Int) : Builder {
    return seconds(TimeGroups.Specific, value.toString())
}

fun Builder.anyMinutes() : Builder {
    return minutes(TimeGroups.Any, "*")
}

fun Builder.minutes(value: Int) : Builder {
    return minutes(TimeGroups.Specific, value.toString())
}

fun Builder.anyHours() : Builder {
    return hours(TimeGroups.Any, "*")
}

fun Builder.hours(value: Int) : Builder {
    return hours(TimeGroups.Specific, value.toString())
}

fun Builder.anyDays() : Builder {
    return days(DayGroups.Any, "*")
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

fun Builder.months(value: Int) : Builder {
    return months(MonthGroups.Specific, value.toString())
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

fun Builder.years(value: Int): Builder {
    return years(YearGroups.Specific, value.toString())
}