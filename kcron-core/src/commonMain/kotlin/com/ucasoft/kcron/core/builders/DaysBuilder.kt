package com.ucasoft.kcron.core.builders

import com.ucasoft.kcron.core.common.DayGroups
import com.ucasoft.kcron.core.exceptions.UnknownCronPart

class DaysBuilder : PartBuilder<DayGroups>() {

    lateinit var days : List<Int>

    override fun build(type: DayGroups, value: String) {
        when (type) {
            DayGroups.Any -> {
                days = listOf(1..31).flatten()
            }
            DayGroups.Specific -> {
                days = value.split(',').map { v -> v.toInt() }.sorted()
            }
            DayGroups.EveryStartingAt -> {
                val data = value.split('/').map { v -> v.toInt() }
                days = listOf(data[0]..31 step data[1]).flatten()
            }
            DayGroups.LastDay -> {
                days = listOf(-32)
            }
            DayGroups.LastWeekday -> {
                days = listOf(32)
            }
            DayGroups.BeforeTheEnd -> {
                days = listOf(value.removePrefix("L").toInt())
            }
            DayGroups.NearestWeekday -> {
                days = listOf(value.removeSuffix("W").toInt() * 100)
            }
            DayGroups.Unknown -> throw UnknownCronPart("days")
        }
    }
}