package com.ucasoft.kcron.builders

import com.ucasoft.kcron.extensions.minusDays
import com.ucasoft.kcron.extensions.plusDays
import kotlinx.datetime.*

interface CronDateTime<out T> {

    val year: Int

    val month: Int

    val dayOfMonth: Int

    val isoDayOfWeek: Int

    val hour: Int

    val minute: Int

    val second: Int

    fun cast() : T

    fun plusDays(days: Int): CronDateTime<T>

    fun minusDays(days: Int): CronDateTime<T>
}

interface CronDateTimeProvider<T, out D> where D: CronDateTime<T> {

    fun now(): D

    fun from(
        year: Int,
        month: Int,
        day: Int,
        hours: Int = 0,
        minutes: Int = 0,
        seconds: Int = 0
    ): D

    fun from(original: T) : D
}

class CronLocalDateTime: CronDateTime<LocalDateTime> {


    private val dateTime: LocalDateTime

    constructor() {
        dateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    constructor(year: Int,
                month: Int,
                day: Int,
                hour: Int,
                minute: Int,
                second: Int) {
        dateTime = LocalDateTime(year, month, day, hour, minute, second)
    }

    override val year: Int
        get() = dateTime.year

    override val month: Int
        get() = dateTime.monthNumber

    override val dayOfMonth: Int
        get() = dateTime.dayOfMonth
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

class CronLocalDateTimeProvider : CronDateTimeProvider<LocalDateTime, CronLocalDateTime> {

    override fun now() = CronLocalDateTime()

    override fun from(
        year: Int,
        month: Int,
        day: Int,
        hours: Int,
        minutes: Int,
        seconds: Int
    ) = CronLocalDateTime(year, month, day, hours, minutes, seconds)

    override fun from(original: LocalDateTime) = original.toCronLocalDateTime()

}

fun LocalDateTime.toCronLocalDateTime() = CronLocalDateTime(this.year, this.monthNumber, this.dayOfMonth, this.hour, this.minute, this.second)