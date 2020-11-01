package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.CronGroups

abstract class AnySpecificEveryAtParser<T>(
    anyPattern: String,
    specificNumberPattern: String,
    specificNamePattern: String = "",
    secondLimitPattern: String = "")
        : AnySpecificParser<T>(anyPattern, specificNumberPattern, specificNamePattern)
        where T: Enum<T>, T: CronGroups {

    override val additionalParts: String = "|((?:${specificNumberPattern})/(?:${if (secondLimitPattern.isNotEmpty()){secondLimitPattern} else {specificNumberPattern}}))"
}