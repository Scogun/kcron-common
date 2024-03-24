package com.ucasoft.kcron.islandtime

import com.ucasoft.kcron.abstractions.CronDateTime
import io.islandtime.DateTime
import io.islandtime.clock.now
import io.islandtime.measures.Days

class CronIslandDateTime: CronDateTime<DateTime> {

    private val dateTime: DateTime

    constructor() {
        dateTime = DateTime.now()
    }

    constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int) {
        dateTime = DateTime(year, month, day, hour, minute, second, 0)
    }

    override val year: Int
        get() = dateTime.year
    override val month: Int
        get() = dateTime.monthNumber
    override val dayOfMonth: Int
        get() = dateTime.dayOfMonth
    override val isoDayOfWeek: Int
        get() = dateTime.dayOfWeek.number
    override val hour: Int
        get() = dateTime.hour
    override val minute: Int
        get() = dateTime.minute
    override val second: Int
        get() = dateTime.second

    override fun cast() = dateTime

    override fun plusDays(days: Int) = dateTime.plus(Days(days)).toCronIslandDateTime()

    override fun minusDays(days: Int) = dateTime.minus(Days(days)).toCronIslandDateTime()
}