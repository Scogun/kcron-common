package com.ucasoft.kcron

import com.ucasoft.kcron.builders.Builder
import com.ucasoft.kcron.parsers.Parser

class KCron {

    companion object {

        fun parseAndBuild(expression: String) : Builder {
            val parseResult = Parser().parse(expression)
            return Builder().build(parseResult.parts)
        }

        fun builder(): Builder {
            return Builder()
        }
    }
}