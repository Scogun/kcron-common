package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.YearGroup
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class YearsBuilder : PartBuilder<YearGroup>() {

    lateinit var years : List<Int>

    override fun build(type: YearGroup, value: String) {
        val now = Clock.System.now()
        val currentYear = now.toLocalDateTime(TimeZone.currentSystemDefault()).year
        if (type == YearGroup.Specific) {
            years = value.split(',').map { d -> d.toInt() }.sorted()
        } else {
            var start = currentYear
            var end = 2099
            var step = 1
            if (type == YearGroup.EveryStartingAt || type == YearGroup.EveryBetween) {
                val data = value.split(if (type == YearGroup.EveryStartingAt) { '/' } else { '-' }).map { v -> v.toInt() }
                start = data[0]
                if (type == YearGroup.EveryStartingAt) {
                    step = data[1]
                } else {
                    end = data[1]
                }
            }
            years = listOf(start..end step step).flatten()
        }
    }
}