package com.ucasoft.kcron.core.parsers

import com.ucasoft.kcron.core.common.CronPart
import com.ucasoft.kcron.core.common.YearGroups

class YearsParser: AnySpecificEveryParser<YearGroups>("\\*", "20[2-9][0-9]", "", "[0-9]|[1-9][1-9]|100") {

    override val unknownGroup = YearGroups.Unknown

    override val groups = YearGroups.entries

    override val part = CronPart.Years
}