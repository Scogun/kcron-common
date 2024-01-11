package com.ucasoft.kcron

import com.ucasoft.kcron.builders.Builder
import com.ucasoft.kcron.builders.CronDateTimeProvider
import com.ucasoft.kcron.builders.CronLocalDateTime
import com.ucasoft.kcron.builders.CronLocalDateTimeProvider
import com.ucasoft.kcron.common.WeekDays
import com.ucasoft.kcron.parsers.Parser
import com.ucasoft.kcron.settings.Settings
import kotlinx.datetime.LocalDateTime

class Cron {

    companion object {

        fun parseAndBuild(expression: String, block: (Settings) -> Unit = {}) : Builder<LocalDateTime, CronLocalDateTime, CronLocalDateTimeProvider> {
            val settings = Settings()
            block.invoke(settings)
            val parseResult = Parser().parse(expression, settings.version)
            return Builder(settings.firstDayOfWeek, CronLocalDateTimeProvider()).build(parseResult.parts)
        }

        fun builder(firstDayOfWeek: WeekDays = WeekDays.Monday): Builder<LocalDateTime, CronLocalDateTime, CronLocalDateTimeProvider> {
            return Builder(firstDayOfWeek, CronLocalDateTimeProvider())
        }
    }
}

@Deprecated(
    message = "Class name should not explicitly tell it's written in Kotlin (with starting 'K'). It would be compiled in Java bytecode, Javascript, etc.",
    replaceWith = ReplaceWith("Cron")
)
typealias KCron = Cron
