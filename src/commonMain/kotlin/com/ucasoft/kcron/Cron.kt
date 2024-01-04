package com.ucasoft.kcron

import com.ucasoft.kcron.builders.Builder
import com.ucasoft.kcron.common.WeekDays
import com.ucasoft.kcron.parsers.Parser
import com.ucasoft.kcron.settings.Settings

class Cron {

    companion object {

        fun parseAndBuild(expression: String, block: (Settings) -> Unit = {}) : Builder {
            val settings = Settings()
            block.invoke(settings)
            val parseResult = Parser().parse(expression, settings.version)
            return Builder(settings.firstDayOfWeek).build(parseResult.parts)
        }

        fun builder(firstDayOfWeek: WeekDays = WeekDays.Monday): Builder {
            return Builder(firstDayOfWeek)
        }
    }
}

@Deprecated(
    message = "Class name should not explicitly tell it's written in Kotlin (with starting 'K'). It would be compiled in Java bytecode, Javascript, etc.",
    replaceWith = ReplaceWith("Cron")
)
typealias KCron = Cron
