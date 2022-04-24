package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.TimeGroups

abstract class TimeBuilder : EveryAtBuilder<TimeGroups>() {

    override val defaultStart = 0

    override val defaultEnd = 59

    override val specific = TimeGroups.Specific

    override val everyBetween = TimeGroups.EveryBetween

    override val everyStartingAt = TimeGroups.EveryStartingAt
}