package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.YearGroups
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class YearsBuilder : EveryAtBuilder<YearGroups>() {

    val years : List<Int>
        get() {
            return values
        }

    override val defaultStart: Int
        get() {
            val now = Clock.System.now()
            return now.toLocalDateTime(TimeZone.currentSystemDefault()).year
        }

    override val defaultEnd = 2099

    override val specific: YearGroups = YearGroups.Specific

    override val everyBetween = YearGroups.EveryBetween

    override val everyStartingAt = YearGroups.EveryStartingAt
}