package com.ucasoft.kcron.common

enum class DayOfWeekGroups(override val index: Int) : CronGroups {

    Unknown(0),
    Any(1),
    Specific(2),
    EveryStartingAt(3),
    Last(4),
    OfMonth(5)
}