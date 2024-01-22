package com.ucasoft.kcron.core.common

enum class TimeGroups(override val index: Int) : CronGroups {

    Unknown(0),
    Any(1),
    Specific(2),
    EveryStartingAt(3),
    EveryBetween(4)
}