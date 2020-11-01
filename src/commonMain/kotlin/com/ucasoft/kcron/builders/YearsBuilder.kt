package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.YearGroups
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class YearsBuilder : PartBuilder<YearGroups>() {

    lateinit var years : List<Int>

    override fun build(type: YearGroups, value: String) {
        val now = Clock.System.now()
        val currentYear = now.toLocalDateTime(TimeZone.currentSystemDefault()).year
        if (type == YearGroups.Specific) {
            years = value.split(',').map { d -> d.toInt() }.sorted()
        } else {
            var start = currentYear
            var end = 2099
            var step = 1
            if (type == YearGroups.EveryStartingAt || type == YearGroups.EveryBetween) {
                val data = value.split(if (type == YearGroups.EveryStartingAt) { '/' } else { '-' }).map { v -> v.toInt() }
                start = data[0]
                if (type == YearGroups.EveryStartingAt) {
                    step = data[1]
                } else {
                    end = data[1]
                }
            }
            years = listOf(start..end step step).flatten()
        }
    }
}