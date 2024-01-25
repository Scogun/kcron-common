package com.ucasoft.kcron.core.parsers

import com.ucasoft.kcron.core.common.CronPart
import com.ucasoft.kcron.core.common.DayGroups

class DaysParser : AnySpecificEveryAtParser<DayGroups>("\\?", "[1-9]|[1-2][0-9]|3[0-1]") {

    override val additionalParts: String
        get() = super.additionalParts + "|(L)|(LW)|(L-(?:${specificNumberPattern}))|((?:${specificNumberPattern})W)"

    override val unknownGroup = DayGroups.Unknown

    override val groups = DayGroups.entries

    override val part = CronPart.Days
}