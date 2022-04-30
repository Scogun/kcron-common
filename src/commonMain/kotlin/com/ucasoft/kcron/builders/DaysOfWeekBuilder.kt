package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.DayOfWeekGroups
import com.ucasoft.kcron.common.WeekDays

class DaysOfWeekBuilder(private val firstWeekDay: WeekDays = WeekDays.Monday) : PartBuilder<DayOfWeekGroups>() {

    lateinit var daysOfWeek: List<Int>

    private val dayWeekNames = WeekDays.values().map { v -> v.shortName }

    override fun build(type: DayOfWeekGroups, value: String)
    {
        when (type) {
            DayOfWeekGroups.Any -> {
                daysOfWeek = listOf(1..7).flatten()
            }
            DayOfWeekGroups.Specific -> {
                val data = value.split(',')
                daysOfWeek = if (data.all { d -> dayWeekNames.contains(d) }) {
                    val firstWeekDayIndex = WeekDays.values().indexOf(firstWeekDay)
                    data.map { d -> dayShift(firstWeekDayIndex, dayWeekNames.indexOf(d)) }.sorted()
                } else {
                    data.map { d -> d.toInt() }.sorted()
                }
            }
            DayOfWeekGroups.EveryStartingAt -> {
                val data = value.split('/').map { v -> v.toInt() }
                daysOfWeek = listOf(data[0]..7 step data[1]).flatten()
            }
            DayOfWeekGroups.Last -> {
                daysOfWeek = listOf(value.removeSuffix("L").toInt() * -1)
            }
            DayOfWeekGroups.OfMonth -> {
                daysOfWeek = value.split('#').map { v -> v.toInt() * 10 }
            }
        }
    }

    private fun dayShift(firstWeekDayIndex: Int, currentDayIndex: Int) : Int
    {
        var index = currentDayIndex - firstWeekDayIndex
        if (index < 0)
        {
            index += 7
        }

        return index + 1
    }
}