package com.ucasoft.kcron.core.parsers

import com.ucasoft.kcron.core.common.CronPart
import com.ucasoft.kcron.core.common.PartValue

data class ParseResult(val parts: Map<CronPart, PartValue>)