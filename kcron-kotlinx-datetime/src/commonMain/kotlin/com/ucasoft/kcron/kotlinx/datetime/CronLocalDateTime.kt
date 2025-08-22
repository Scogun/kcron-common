package com.ucasoft.kcron.kotlinx.datetime

import com.ucasoft.kcron.abstractions.CronDateTime
import kotlinx.datetime.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class CronLocalDateTime: CronDateTime<LocalDateTime> {


    private val dateTime: LocalDateTime

    @OptIn(ExperimentalTime::class)
    constructor() {
        dateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int) {
        dateTime = LocalDateTime(year, month, day, hour, minute, second)
    }

    override val year: Int
        get() = dateTime.year

    override val month: Int
        get() = dateTime.month.number

    override val dayOfMonth: Int
        get() = dateTime.day

    override val isoDayOfWeek: Int
        get() = dateTime.dayOfWeek.isoDayNumber

    override val hour: Int
        get() = dateTime.hour

    override val minute: Int
        get() = dateTime.minute

    override val second: Int
        get() = dateTime.second

    override fun cast() = dateTime

    override fun plusDays(days: Int) = dateTime.plusDays(days).toCronLocalDateTime()

    override fun minusDays(days: Int) = dateTime.minusDays(days).toCronLocalDateTime()
}