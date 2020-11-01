package com.ucasoft.kcron.common

enum class DayGroups(override val index: Int) : CronGroups {

    Unknown(0),
    Any(1),
    Specific(2),
    EveryStartingAt(3),
    LastDay(4),
    LastWeekday(5),
    BeforeTheEnd(6),
    NearestWeekday(7)
}