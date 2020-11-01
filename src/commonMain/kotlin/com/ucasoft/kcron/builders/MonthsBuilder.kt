package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.MonthGroups

class MonthsBuilder : PartBuilder<MonthGroups>() {

    lateinit var months: List<Int>

    private val monthNames = listOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")

    override fun build(type: MonthGroups, value: String) {
        if (type == MonthGroups.Specific) {
            val data = value.split(',')
            months = if (data.all { d -> monthNames.contains(d) }) {
                data.map { d -> monthNames.indexOf(d) + 1 }.sorted()
            } else {
                data.map { d -> d.toInt() }.sorted()
            }
        } else {
            var start = 1
            var end = 12
            var step = 1
            if (type == MonthGroups.EveryStartingAt || type == MonthGroups.EveryBetween) {
                val data = value.split(if (type == MonthGroups.EveryStartingAt) { '/' } else { '-' }).map { v -> v.toInt() }
                start = data[0]
                if (type == MonthGroups.EveryStartingAt) {
                    step = data[1]
                } else {
                    end = data[1]
                }
            }
            months = listOf(start..end step step).flatten()
        }
    }
}