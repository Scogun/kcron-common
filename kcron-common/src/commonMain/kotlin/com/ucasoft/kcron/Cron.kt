package com.ucasoft.kcron

import com.ucasoft.kcron.core.common.WeekDays
import com.ucasoft.kcron.core.settings.Settings
import com.ucasoft.kcron.kotlinx.datetime.CronLocalDateTimeProvider
import com.ucasoft.kcron.core.Cron as CoreCron

object Cron {

    private val dateTimeProvider = CronLocalDateTimeProvider()

    fun parseAndBuild(expression: String, block: (Settings) -> Unit = {})  = CoreCron.parseAndBuild(expression, dateTimeProvider, block)

    fun builder(firstDayOfWeek: WeekDays = WeekDays.Monday) = CoreCron.builder(dateTimeProvider, firstDayOfWeek)
}

@Deprecated(
    message = "Class name should not explicitly tell it's written in Kotlin (with starting 'K'). It would be compiled in Java bytecode, Javascript, etc.",
    replaceWith = ReplaceWith("Cron")
)
typealias KCron = Cron