package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.DayOfWeekGroups

class DaysOfWeekBuilder : PartBuilder<DayOfWeekGroups>() {

    lateinit var daysOfWeek: List<Int>

    private val dayWeekNames = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

    override fun build(type: DayOfWeekGroups, value: String)
    {
        when (type) {
            DayOfWeekGroups.Any -> {
                daysOfWeek = listOf(1..7).flatten()
            }
            DayOfWeekGroups.Specific -> {
                val data = value.split(',')
                daysOfWeek = if (data.all { d -> dayWeekNames.contains(d) }) {
                    data.map { d -> dayWeekNames.indexOf(d) + 1 }.sorted()
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
}