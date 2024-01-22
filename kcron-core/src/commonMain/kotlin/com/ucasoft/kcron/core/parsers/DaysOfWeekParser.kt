package com.ucasoft.kcron.core.parsers

import com.ucasoft.kcron.core.common.CronPart
import com.ucasoft.kcron.core.common.DayOfWeekGroups

class DaysOfWeekParser : AnySpecificEveryAtParser<DayOfWeekGroups>("[*?]", "[1-7]", listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT").joinToString("|")) {

    override val additionalParts: String
        get() = super.additionalParts + "|((?:$specificNumberPattern)L)|((?:(?:$specificNumberPattern)|(?:$specificNamePattern))#[1-5])"

    override val unknownGroup = DayOfWeekGroups.Unknown

    override val groups = DayOfWeekGroups.entries

    override val part = CronPart.DaysOfWeek
}