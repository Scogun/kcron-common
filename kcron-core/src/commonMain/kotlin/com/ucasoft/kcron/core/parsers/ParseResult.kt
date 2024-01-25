package com.ucasoft.kcron.core.parsers

import com.ucasoft.kcron.core.common.CronPart
import com.ucasoft.kcron.core.common.PartValue

class ParseResult {

    val parts = mutableMapOf<CronPart, PartValue>()
}