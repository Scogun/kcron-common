package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.CronPart
import com.ucasoft.kcron.common.YearGroups

class YearsParser: AnySpecificEveryParser<YearGroups>("\\*", "20[2-9][0-9]", "", "[0-9]|[1-9][1-9]|100") {

    override val unknownGroup = YearGroups.Unknown

    override val groups = YearGroups.values()

    override val part = CronPart.Years
}