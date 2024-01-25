package com.ucasoft.kcron.core.parsers

import com.ucasoft.kcron.core.common.CronGroups

abstract class AnySpecificEveryAtParser<T>(
    anyPattern: String,
    specificNumberPattern: String,
    specificNamePattern: String = "",
    private val secondLimitPattern: String = "")
        : AnySpecificParser<T>(anyPattern, specificNumberPattern, specificNamePattern)
        where T: Enum<T>, T: CronGroups {

    override val additionalParts: String
        get() {
            if (specificNamePattern.isNotEmpty()) {
                return "|((?:(?:${specificNumberPattern})|(?:${specificNamePattern}))/(?:${secondLimitPattern.ifEmpty { specificNumberPattern }}))"
            }

            return "|((?:${specificNumberPattern})/(?:${secondLimitPattern.ifEmpty { specificNumberPattern }}))"
        }
}