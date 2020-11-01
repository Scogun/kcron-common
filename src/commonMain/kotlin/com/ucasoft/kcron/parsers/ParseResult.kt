package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.CronPart
import com.ucasoft.kcron.common.PartValue

class ParseResult {

    val parts = mutableMapOf<CronPart, PartValue>()
}