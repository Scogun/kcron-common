package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.CronPart
import com.ucasoft.kcron.common.MonthGroups

class MonthsParser : AnySpecificEveryParser<MonthGroups>("\\*", "[1-9]|1[0-2]", listOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC").joinToString("|")) {

    override val unknownGroup = MonthGroups.Unknown

    override val groups = MonthGroups.values()

    override val part = CronPart.Months
}