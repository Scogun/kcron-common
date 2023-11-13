package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.CronPart
import com.ucasoft.kcron.common.TimeGroups

abstract class TimeParser(override val part: CronPart, startingPattern: String, everyPattern: String) : AnySpecificEveryParser<TimeGroups>("\\*", startingPattern, "", everyPattern) {

    override val unknownGroup = TimeGroups.Unknown

    override val groups = TimeGroups.entries
}