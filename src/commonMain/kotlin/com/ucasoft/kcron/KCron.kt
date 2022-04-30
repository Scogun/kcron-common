package com.ucasoft.kcron

import com.ucasoft.kcron.builders.Builder
import com.ucasoft.kcron.common.WeekDays
import com.ucasoft.kcron.parsers.Parser

class KCron {

    companion object {

        fun parseAndBuild(expression: String, firstDayOfWeek: WeekDays = WeekDays.Monday) : Builder {
            val parseResult = Parser().parse(expression)
            return Builder(firstDayOfWeek).build(parseResult.parts)
        }

        fun builder(firstDayOfWeek: WeekDays = WeekDays.Monday): Builder {
            return Builder(firstDayOfWeek)
        }
    }
}