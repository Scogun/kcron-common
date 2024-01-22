package com.ucasoft.kcron.core.builders

import com.ucasoft.kcron.abstractions.CronDateTimeProvider
import com.ucasoft.kcron.core.common.YearGroups

class YearsBuilder<P: CronDateTimeProvider<*, *>>(private val provider: P) : EveryAtBuilder<YearGroups>() {

    val years : List<Int>
        get() {
            return values
        }

    override val defaultStart: Int
        get() {
            val now = provider.now()
            return now.year
        }

    override val defaultEnd = 2099

    override val specific: YearGroups = YearGroups.Specific

    override val everyBetween = YearGroups.EveryBetween

    override val everyStartingAt = YearGroups.EveryStartingAt
}