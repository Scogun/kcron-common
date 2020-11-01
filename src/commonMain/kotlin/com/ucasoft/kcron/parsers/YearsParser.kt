package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.YearGroup

class YearsParser: AnySpecificEveryParser<YearGroup>("\\*", "20[2-9][0-9]", "", "[0-9]|[1-9][1-9]|100") {

    override val unknownGroup = YearGroup.Unknown

    override val groups = YearGroup.values()

    override val partName = "years"
}