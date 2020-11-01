package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.*
import com.ucasoft.kcron.extensions.*
import kotlinx.datetime.*

class Builder {

    private val partBuilders = mapOf(
        CronPart.Seconds to SecondsBuilder(),
        CronPart.Minutes to MinutesBuilder(),
        CronPart.Hours to HoursBuilder(),
        CronPart.Days to DaysBuilder(),
        CronPart.Months to MonthsBuilder(),
        CronPart.DaysOfWeek to DaysOfWeekBuilder(),
        CronPart.Years to YearsBuilder()
    )

    init {
        anySeconds()
        anyMinutes()
        anyHours()
        anyDays()
        anyMonths()
        anyDaysOfWeek()
        anyYears()
    }

    val nextRun : LocalDateTime?
        get() = nextRunList(1).firstOrNull()

    fun build(parts: Map<CronPart, PartValue>): Builder {
        parts.forEach { entry -> partBuilders[entry.key]?.commonBuild(entry.value.type, entry.value.value) }
        return this
    }

    fun seconds(type: TimeGroups, value: String) : Builder {
        partBuilders.getValue(CronPart.Seconds).commonBuild(type, value)
        return this
    }

    fun minutes(type: TimeGroups, value: String) : Builder {
        partBuilders.getValue(CronPart.Minutes).commonBuild(type, value)
        return this
    }

    fun hours(type: TimeGroups, value: String) : Builder {
        partBuilders.getValue(CronPart.Hours).commonBuild(type, value)
        return this
    }

    fun days(type: DayGroups, value: String) : Builder {
        if (type != DayGroups.Any) {
            anyDaysOfWeek()
        }
        partBuilders.getValue(CronPart.Days).commonBuild(type, value)
        return this
    }

    fun months(type: MonthGroups, value: String) : Builder {
        partBuilders.getValue(CronPart.Months).commonBuild(type, value)
        return this
    }

    fun daysOfWeek(type: DayOfWeekGroups, value: String) : Builder {
        if (type != DayOfWeekGroups.Any) {
            anyDays()
        }
        partBuilders.getValue(CronPart.DaysOfWeek).commonBuild(type, value)
        return this
    }

    fun years(type: YearGroups, value: String) : Builder {
        partBuilders.getValue(CronPart.Years).commonBuild(type, value)
        return this
    }

    fun nextRunList(maxCount: Int = 10) : List<LocalDateTime> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val result = mutableListOf<LocalDateTime>()
        for (year in (partBuilders.getValue(CronPart.Years) as YearsBuilder).years.filter { y -> y >= now.year }) {
            for (month in (partBuilders.getValue(CronPart.Months) as MonthsBuilder).months.filter { m -> m >= now.monthNumber || year > now.year}) {
                for (day in calculateDays(year, month, (partBuilders.getValue(CronPart.DaysOfWeek) as DaysOfWeekBuilder).daysOfWeek, (partBuilders.getValue(CronPart.Days) as DaysBuilder).days, now)) {
                    for (hour in (partBuilders.getValue(CronPart.Hours) as HoursBuilder).hours.filter { h -> h >= now.hour || day > now.dayOfMonth || month > now.monthNumber || year > now.year }) {
                        for (minute in (partBuilders.getValue(CronPart.Minutes) as MinutesBuilder).minutes.filter { m -> m >= now.minute || hour > now.hour || day > now.dayOfMonth || month > now.monthNumber || year > now.year }) {
                            for (seconds in (partBuilders.getValue(CronPart.Seconds) as SecondsBuilder).seconds.filter { s -> s > now.second || minute > now.minute || hour > now.hour || day > now.dayOfMonth || month > now.monthNumber || year > now.year }) {
                                result.add(LocalDateTime(year, month, day, hour, minute, seconds))
                                if (result.size == maxCount) {
                                    return result
                                }
                            }
                        }
                    }
                }
            }
        }

        return result
    }

    private fun calculateDays(year: Int, month: Int, daysOfWeek: List<Int>, days: List<Int>, now: LocalDateTime): List<Int> {
        var lastDay = lastMonthDay(year, month)
        val lastDayInt = lastDay.dayOfMonth
        if (daysOfWeek.isNotEmpty() && daysOfWeek.size < 7) {
            return calculateDaysBasedOnDaysOfWeek(year, month, daysOfWeek, now)
        }
        if (days.isNotEmpty()) {
            val firstValue = days[0]
            if (firstValue == -32) {
                return listOf(lastDayInt)
            }
            if (firstValue < 0) {
                return listOf(lastDay.minusDays(firstValue).dayOfMonth)
            }
            if (firstValue == 32) {
                if (lastDay.dayOfWeek.isoDayNumber >= 6) {
                    lastDay = lastDay.minusDays(lastDay.dayOfWeek.isoDayNumber - 5)
                }
                return listOf(lastDay.dayOfMonth)
            }
            if (firstValue >= 100) {
                val nearestTo = firstValue / 100
                if (nearestTo > lastDayInt) {
                    return emptyList()
                }
                return listOf(nearestWorkDayTo(year, month, nearestTo))
            }
        }
        return days.filter { d -> (d >= now.dayOfMonth || month > now.monthNumber || year > now.year) && (d <= lastDayInt) }
    }

    private fun calculateDaysBasedOnDaysOfWeek(
        year: Int,
        month: Int,
        daysOfWeek: List<Int>,
        now: LocalDateTime
    ): List<Int> {
        val lastDay = lastMonthDay(year, month)
        val lastDayInt = lastDay.dayOfMonth
        val firstValue = daysOfWeek[0]
        if (firstValue < 0) {
            val lastOf = toIsoDayOfWeek(firstValue * -1)
            var difference = lastDay.dayOfWeek.isoDayNumber - lastOf
            if (difference < 0) {
                difference += 7
            }
            return listOf(lastDay.minusDays(difference).dayOfMonth)
        }
        if (firstValue >= 10) {
            val dayOfWeek = toIsoDayOfWeek(firstValue / 10)
            val index = daysOfWeek[1] / 10
            val firstInMonth = LocalDateTime(year, month, 1, 0, 0)
            var difference = dayOfWeek - firstInMonth.dayOfWeek.isoDayNumber
            if (difference < 0) {
                difference += 7
            }
            val inMonth = firstInMonth.plusDays(difference).dayOfMonth + 7 * (index - 1)
            if (inMonth > lastDayInt) {
                return emptyList()
            }
            return listOf(inMonth)
        }
        val firstDay = if (now.monthNumber < month || now.year < year) {
            1
        } else {
            now.dayOfMonth + 1
        }
        val result = mutableListOf<Int>()
        val isoDaysOfWeek = daysOfWeek.map { d -> toIsoDayOfWeek(d) }
        for (day in firstDay..lastDayInt) {
            val weekDay = LocalDateTime(year, month, day, 0, 0).dayOfWeek.isoDayNumber
            if (isoDaysOfWeek.contains(weekDay)) {
                result.add(day)
            }
            if (result.size == daysOfWeek.size) {
                break
            }
        }
        while (result.isNotEmpty()) {
            var index = result.size - daysOfWeek.size
            if (index < 0) {
                index += daysOfWeek.size - 1
            }
            val nextDay = result[index] + 7
            if (nextDay > lastDayInt) {
                break
            }
            result.add(nextDay)
        }
        return result
    }

    private fun nearestWorkDayTo(year: Int, month: Int, day: Int) : Int {
        var checkingDay = LocalDateTime(year, month, day, 0, 0)
        if (checkingDay.dayOfWeek.isoDayNumber >= 6) {
            checkingDay = if (checkingDay.dayOfWeek.isoDayNumber == 6) {
                if (checkingDay.dayOfMonth > 1) {
                    checkingDay.minusDays(1)
                } else {
                    checkingDay.plusDays(2)
                }
            } else if (checkingDay.dayOfMonth < lastMonthDay(year, month).dayOfMonth) {
                checkingDay.plusDays(1)
            } else {
                checkingDay.minusDays(2)
            }
        }
        return checkingDay.dayOfMonth
    }

    private fun toIsoDayOfWeek(dayOfWeek: Int) : Int {
        if (dayOfWeek == 1) {
            return 7
        }
        return dayOfWeek - 1
    }

    private fun lastMonthDay(year: Int, month: Int): LocalDateTime {
        var nextMonth = month + 1
        var nextYear = year
        if (nextMonth > 12) {
            nextYear += 1
            nextMonth = 1
        }
        val firstDayNextMonth = LocalDateTime(nextYear, nextMonth, 1, 0, 0).toInstant(TimeZone.currentSystemDefault())
        return firstDayNextMonth.plus(-1, DateTimeUnit.DAY, TimeZone.currentSystemDefault()).toLocalDateTime(TimeZone.currentSystemDefault())
    }
}