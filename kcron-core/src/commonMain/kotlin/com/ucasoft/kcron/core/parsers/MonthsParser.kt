package com.ucasoft.kcron.core.parsers

import com.ucasoft.kcron.core.common.CronPart
import com.ucasoft.kcron.core.common.MonthGroups

class MonthsParser : AnySpecificEveryParser<MonthGroups>("\\*", "[1-9]|1[0-2]", listOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC").joinToString("|")) {

    override val unknownGroup = MonthGroups.Unknown

    override val groups = MonthGroups.entries

    override val part = CronPart.Months
}