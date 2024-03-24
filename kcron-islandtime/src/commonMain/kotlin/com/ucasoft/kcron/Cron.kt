package com.ucasoft.kcron

import com.ucasoft.kcron.core.builders.Builder
import com.ucasoft.kcron.core.common.WeekDays
import com.ucasoft.kcron.core.Cron as CronCore
import com.ucasoft.kcron.core.cron as Cron_core
import com.ucasoft.kcron.core.settings.Settings
import com.ucasoft.kcron.islandtime.CronIslandDateTime
import com.ucasoft.kcron.islandtime.CronIslandDateTimeProvider
import io.islandtime.DateTime

object Cron {

    private val dateTimeProvider = CronIslandDateTimeProvider()

    fun parseAndBuild(expression: String, block: (Settings) -> Unit = {})  = CronCore.parseAndBuild(expression, dateTimeProvider, block)

    fun builder(firstDayOfWeek: WeekDays = WeekDays.Monday) = CronCore.builder(dateTimeProvider, firstDayOfWeek)
}

fun cron(
    firstDayOfWeek: WeekDays = WeekDays.Monday,
    body: Builder<DateTime, CronIslandDateTime, CronIslandDateTimeProvider>.() -> Unit
) = Cron_core(CronIslandDateTimeProvider(), firstDayOfWeek, body)