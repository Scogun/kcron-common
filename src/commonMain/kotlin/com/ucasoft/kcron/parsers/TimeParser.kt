package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.TimeGroups

abstract class TimeParser(override val partName: String, startingPattern: String, everyPattern: String) : AnySpecificEveryParser<TimeGroups>("\\*", startingPattern, "", everyPattern) {

    override val unknownGroup = TimeGroups.Unknown

    override val groups = TimeGroups.values()
}