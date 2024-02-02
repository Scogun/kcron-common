package com.ucasoft.kcron.core

import com.ucasoft.kcron.abstractions.CronDateTime
import com.ucasoft.kcron.abstractions.CronDateTimeProvider
import com.ucasoft.kcron.core.builders.Builder
import com.ucasoft.kcron.core.common.WeekDays
import com.ucasoft.kcron.core.parsers.Parser
import com.ucasoft.kcron.core.settings.Settings

object Cron {

    fun <T, D : CronDateTime<T>, P : CronDateTimeProvider<T, D>> parseAndBuild(
        expression: String,
        provider: P,
        block: (Settings) -> Unit = {}
    ): Builder<T, D, P> {
        val settings = Settings()
        block.invoke(settings)
        val parseResult = Parser().parse(expression, settings.version)
        return Builder(provider, settings.firstDayOfWeek).build(parseResult.parts)
    }

    fun <T, D : CronDateTime<T>, P : CronDateTimeProvider<T, D>> builder(
        provider: P,
        firstDayOfWeek: WeekDays = WeekDays.Monday
    ): Builder<T, D, P> {
        return Builder(provider, firstDayOfWeek)
    }
}

fun <T, D : CronDateTime<T>, P : CronDateTimeProvider<T, D>> cron(
    provider: P,
    firstDayOfWeek: WeekDays = WeekDays.Monday,
    body: Builder<T, D, P>.() -> Unit
) = Cron.builder(provider, firstDayOfWeek).apply(body)