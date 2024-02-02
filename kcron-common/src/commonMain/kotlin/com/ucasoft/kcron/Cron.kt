package com.ucasoft.kcron

import com.ucasoft.kcron.core.builders.Builder
import com.ucasoft.kcron.core.common.WeekDays
import com.ucasoft.kcron.core.settings.Settings
import com.ucasoft.kcron.kotlinx.datetime.CronLocalDateTime
import com.ucasoft.kcron.kotlinx.datetime.CronLocalDateTimeProvider
import kotlinx.datetime.LocalDateTime
import com.ucasoft.kcron.core.Cron as CoreCron
import com.ucasoft.kcron.core.cron as Core_cron

object Cron {

    private val dateTimeProvider = CronLocalDateTimeProvider()

    fun parseAndBuild(expression: String, block: (Settings) -> Unit = {})  = CoreCron.parseAndBuild(expression, dateTimeProvider, block)

    fun builder(firstDayOfWeek: WeekDays = WeekDays.Monday) = CoreCron.builder(dateTimeProvider, firstDayOfWeek)
}

fun cron(
    firstDayOfWeek: WeekDays = WeekDays.Monday,
    body: Builder<LocalDateTime, CronLocalDateTime, CronLocalDateTimeProvider>.() -> Unit
) = Core_cron(CronLocalDateTimeProvider(), firstDayOfWeek, body)

@Deprecated(
    message = "Class name should not explicitly tell it's written in Kotlin (with starting 'K'). It would be compiled in Java bytecode, Javascript, etc.",
    replaceWith = ReplaceWith("Cron")
)
typealias KCron = Cron