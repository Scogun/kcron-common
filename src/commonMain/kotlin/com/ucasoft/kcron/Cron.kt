package com.ucasoft.kcron

import com.ucasoft.kcron.builders.Builder
import com.ucasoft.kcron.builders.CronLocalDateTime
import com.ucasoft.kcron.common.WeekDays
import com.ucasoft.kcron.parsers.Parser
import com.ucasoft.kcron.settings.Settings
import kotlinx.datetime.LocalDateTime

class Cron {

    companion object {

        fun parseAndBuild(expression: String, block: (Settings) -> Unit = {}) : Builder<LocalDateTime, CronLocalDateTime> {
            val settings = Settings()
            block.invoke(settings)
            val parseResult = Parser().parse(expression, settings.version)
            return Builder(settings.firstDayOfWeek, CronLocalDateTime()).build(parseResult.parts)
        }

        fun builder(firstDayOfWeek: WeekDays = WeekDays.Monday): Builder<LocalDateTime, CronLocalDateTime> {
            return Builder(firstDayOfWeek, CronLocalDateTime())
        }
    }
}

@Deprecated(
    message = "Class name should not explicitly tell it's written in Kotlin (with starting 'K'). It would be compiled in Java bytecode, Javascript, etc.",
    replaceWith = ReplaceWith("Cron")
)
typealias KCron = Cron
