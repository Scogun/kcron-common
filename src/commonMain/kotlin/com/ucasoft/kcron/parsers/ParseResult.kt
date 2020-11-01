package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.CronPart

class ParseResult {

    val parts = mutableMapOf<CronPart, PartResult>()
}