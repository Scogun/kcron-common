package com.ucasoft.kcron.core.builders

import com.ucasoft.kcron.abstractions.CronDateTime
import com.ucasoft.kcron.abstractions.CronDateTimeProvider
import com.ucasoft.kcron.core.common.*

class Builder<T, D: CronDateTime<T>, P: CronDateTimeProvider<T, D>>(private val dateTimeProvider: P, firstDayOfWeek: WeekDays = WeekDays.Monday) {

    private val partBuilders = mapOf(
        CronPart.Seconds to SecondsBuilder(),
        CronPart.Minutes to MinutesBuilder(),
        CronPart.Hours to HoursBuilder(),
        CronPart.Days to DaysBuilder(),
        CronPart.Months to MonthsBuilder(),
        CronPart.DaysOfWeek to DaysOfWeekBuilder(firstDayOfWeek),
        CronPart.Years to YearsBuilder(dateTimeProvider)
    )

    init {
        seconds(TimeGroups.Any, "*")
        minutes(TimeGroups.Any, "*")
        hours(TimeGroups.Any, "*")
        days(DayGroups.Any, "?")
        months(MonthGroups.Any, "*")
        daysOfWeek(DayOfWeekGroups.Any, "*")
        years(YearGroups.Any, "*")
    }

    @OptIn(DelicateIterableApi::class)
    val nextRun : T?
        get() = asIterable().firstOrNull()

    val expression: String
        get() = partBuilders.values.joinToString(" ") { it.value }

    private val firstDayOfWeekIndex = WeekDays.entries.indexOf(firstDayOfWeek)

    fun build(parts: Map<CronPart, PartValue>) = apply {
        parts.forEach { entry -> partBuilders[entry.key]?.commonBuild(entry.value.type, entry.value.value) }
    }

    fun seconds(type: TimeGroups, value: String) = apply {
        partBuilders.getValue(CronPart.Seconds).commonBuild(type, value)
    }

    fun minutes(type: TimeGroups, value: String) = apply {
        partBuilders.getValue(CronPart.Minutes).commonBuild(type, value)
    }

    fun hours(type: TimeGroups, value: String) = apply {
        partBuilders.getValue(CronPart.Hours).commonBuild(type, value)
    }

    fun days(type: DayGroups, value: String) : Builder<T, D, P> {
        if (type != DayGroups.Any) {
            daysOfWeek(DayOfWeekGroups.Any, "?")
        } else if (partBuilders.getValue(CronPart.DaysOfWeek).value == "?") {
            daysOfWeek(DayOfWeekGroups.Any, "*")
        }
        partBuilders.getValue(CronPart.Days).commonBuild(type, value)
        return this
    }

    fun months(type: MonthGroups, value: String) = apply {
        partBuilders.getValue(CronPart.Months).commonBuild(type, value)
    }

    fun daysOfWeek(type: DayOfWeekGroups, value: String) : Builder<T, D, P> {
        if (type != DayOfWeekGroups.Any) {
            days(DayGroups.Any, "?")
        }
        partBuilders.getValue(CronPart.DaysOfWeek).commonBuild(type, value)
        return this
    }

    fun years(type: YearGroups, value: String) = apply {
        partBuilders.getValue(CronPart.Years).commonBuild(type, value)
    }

    @DelicateIterableApi
    fun asIterable(from: T): Iterable<T> {
        val internalStart = dateTimeProvider.from(from)
        return Iterable {
            iterator {
                getYears(internalStart) { year ->
                    getMonths(internalStart, year) { month ->
                        getDays(internalStart, year, month) { day ->
                            getHours(internalStart, year, month, day) { hour ->
                                getMinutes(internalStart, year, month, day, hour) { minute ->
                                    getSeconds(internalStart, year, month, day, hour, minute) {
                                        yield(dateTimeProvider.from(year, month, day, hour, minute, it).cast())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @DelicateIterableApi
    fun asIterable() = asIterable(dateTimeProvider.now().cast())

    @Deprecated(
        message = "Use asIterable().take(maxCount) instead.",
        replaceWith = ReplaceWith(
            expression = "asIterable().take(maxCount)",
            imports = [
                "kotlin.collections.toList",
            ]
        ),
    )
    @OptIn(DelicateIterableApi::class)
    fun nextRunList(maxCount: Int = 10) : List<T> {
        return asIterable().take(maxCount)
    }

    private inline fun getYears(from: D, block: (Int) -> Unit) {
        val yearsBuilder = partBuilders.getValue(CronPart.Years) as YearsBuilder<*>
        yearsBuilder.years.filter { it >= from.year }.forEach(block)
    }

    private inline fun getMonths(from: D, year: Int, block: (Int) -> Unit) {
        val monthsBuilder = partBuilders.getValue(CronPart.Months) as MonthsBuilder
        monthsBuilder.months.filter { it >= from.month || year > from.year }.forEach(block)
    }

    private inline fun getDays(from: D, year: Int, month: Int, block: (Int) -> Unit) {
        val daysOfWeekBuilder = partBuilders.getValue(CronPart.DaysOfWeek) as DaysOfWeekBuilder
        val daysBuilder = partBuilders.getValue(CronPart.Days) as DaysBuilder
        calculateDays(year, month, daysOfWeekBuilder.daysOfWeek, daysBuilder.days, from).forEach(block)
    }

    private inline fun getHours(from: D, year: Int, month: Int, day: Int, block: (Int) -> Unit) {
        val hoursBuilder = partBuilders.getValue(CronPart.Hours) as HoursBuilder
        hoursBuilder.hours.filter { it >= from.hour || day > from.dayOfMonth || month > from.month || year > from.year }.forEach(block)
    }

    private inline fun getMinutes(from: D, year: Int, month: Int, day: Int, hour: Int, block: (Int) -> Unit) {
        val minutesBuilder = partBuilders.getValue(CronPart.Minutes) as MinutesBuilder
        minutesBuilder.minutes.filter { it >= from.minute || hour > from.hour || day > from.dayOfMonth || month > from.month || year > from.year }.forEach(block)
    }

    private inline fun getSeconds(from: D, year: Int, month: Int, day: Int, hour: Int, minute: Int, block: (Int) -> Unit) {
        val secondsBuilder = partBuilders.getValue(CronPart.Seconds) as SecondsBuilder
        secondsBuilder.seconds.filter { it > from.second || minute > from.minute || hour > from.hour || day > from.dayOfMonth || month > from.month || year > from.year }.forEach(block)
    }

    private fun calculateDays(year: Int, month: Int, daysOfWeek: List<Int>, days: List<Int>, now: CronDateTime<T>): List<Int> {
        var lastDay = lastMonthDay(year, month)
        val lastDayInt = lastDay.dayOfMonth
        if (daysOfWeek.isNotEmpty() && daysOfWeek.size < 7) {
            return calculateDaysBasedOnDaysOfWeek(year, month, lastDay, daysOfWeek, now)
        }
        if (days.isNotEmpty()) {
            val firstValue = days[0]
            when {
                firstValue == -32 -> return listOf(lastDayInt)
                firstValue < 0 -> return listOf(lastDay.minusDays(firstValue).dayOfMonth)
                firstValue == 32 -> {
                    if (lastDay.isoDayOfWeek >= 6) {
                        lastDay = lastDay.minusDays(lastDay.isoDayOfWeek - 5)
                    }
                    return listOf(lastDay.dayOfMonth)
                }
                firstValue >= 100 -> {
                    val nearestTo = firstValue / 100
                    if (nearestTo > lastDayInt) {
                        return emptyList()
                    }
                    return listOf(nearestWorkDayTo(year, month, lastDayInt, nearestTo))
                }
            }
        }
        return days.filter { d -> (d >= now.dayOfMonth || month > now.month || year > now.year) && (d <= lastDayInt) }
    }

    private fun calculateDaysBasedOnDaysOfWeek(
        year: Int,
        month: Int,
        lastMonthDay: CronDateTime<T>,
        daysOfWeek: List<Int>,
        now: CronDateTime<T>
    ): List<Int> {
        val firstValue = daysOfWeek[0]
        if (firstValue < 0) {
            val lastOf = toIsoDayOfWeek(firstValue * -1)
            var difference = lastMonthDay.isoDayOfWeek - lastOf
            if (difference < 0) {
                difference += 7
            }
            return listOf(lastMonthDay.minusDays(difference).dayOfMonth)
        }
        val lastDayInt = lastMonthDay.dayOfMonth
        val firstInMonth = dateTimeProvider.from(year, month, 1)
        val startDay = if (now.month == month && now.year == year) { now } else { firstInMonth }
        if (firstValue >= 10) {
            val dayOfWeek = toIsoDayOfWeek(firstValue / 10)
            val index = daysOfWeek[1] / 10
            val inMonth = dayOfWeekByIndex(dayOfWeek, index, firstInMonth, lastDayInt) ?: return emptyList()
            if (inMonth < startDay.dayOfMonth) {
                return emptyList()
            }
            return listOf(inMonth)
        }
        val result = daysOfWeek.mapNotNull { d -> dayOfWeekByIndex(toIsoDayOfWeek(d), 1, startDay, lastDayInt) }.sorted().toMutableList()
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

    private fun dayOfWeekByIndex(dayOfWeek: Int, index: Int, startDay: CronDateTime<T>, lastDayInt: Int): Int? {
        var difference = dayOfWeek - startDay.isoDayOfWeek
        if (difference < 0) {
            difference += 7
        }
        val inMonth = startDay.dayOfMonth + difference + 7 * (index - 1)
        if (inMonth <= lastDayInt) {
            return inMonth
        }
        return null
    }

    @Suppress("UNCHECKED_CAST")
    private fun nearestWorkDayTo(year: Int, month: Int, lastMonthDay: Int, day: Int) : Int {
        var checkingDay = dateTimeProvider.from(year, month, day)
        if (checkingDay.isoDayOfWeek >= 6) {
            checkingDay = if (checkingDay.isoDayOfWeek == 6) {
                if (checkingDay.dayOfMonth > 1) {
                    checkingDay.minusDays(1) as D
                } else {
                    checkingDay.plusDays(2) as D
                }
            } else if (checkingDay.dayOfMonth < lastMonthDay) {
                checkingDay.plusDays(1) as D
            } else {
                checkingDay.minusDays(2) as D
            }
        }
        return checkingDay.dayOfMonth
    }

    private fun toIsoDayOfWeek(dayOfWeek: Int) : Int {
        var index = dayOfWeek + firstDayOfWeekIndex
        if (index > 7) {
            index -= 7
        }
        return index
    }

    private fun lastMonthDay(year: Int, month: Int): CronDateTime<T> {
        var nextMonth = month + 1
        var nextYear = year
        if (nextMonth > 12) {
            nextYear += 1
            nextMonth = 1
        }
        val firstDayNextMonth = dateTimeProvider.from(nextYear, nextMonth, 1)
        return firstDayNextMonth.minusDays(1)
    }
}