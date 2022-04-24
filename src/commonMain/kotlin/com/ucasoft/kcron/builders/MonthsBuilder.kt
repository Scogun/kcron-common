package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.MonthGroups

class MonthsBuilder : EveryAtBuilder<MonthGroups>() {

    val months: List<Int>
        get() {
            return values
        }

    private val monthNames = listOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")

    override val defaultStart = 1

    override val defaultEnd = 12

    override val specific = MonthGroups.Specific

    override val everyBetween = MonthGroups.EveryBetween

    override val everyStartingAt = MonthGroups.EveryStartingAt

    override fun calculateSpecific(value: String): List<Int>{
        val data = value.split(',')
        return if (data.all { d -> monthNames.contains(d) }) {
            data.map { d -> monthNames.indexOf(d) + 1 }.sorted()
        } else {
            data.map { d -> d.toInt() }.sorted()
        }
    }
}