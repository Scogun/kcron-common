package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.CronGroups

abstract class AnySpecificEveryParser<T>(
    anyPattern: String,
    specificNumberPattern: String,
    specificNamePattern: String = "",
    secondNumberPattern: String = "")
        : AnySpecificEveryAtParser<T>(anyPattern, specificNumberPattern, specificNamePattern, secondNumberPattern)
        where T: Enum<T>, T: CronGroups {

    override val additionalParts: String
        get() {
            val betweenPattern = if (specificNamePattern.isNotEmpty()) {
                "((?:(?:${specificNumberPattern})-(?:${specificNumberPattern}))|(?:(?:${specificNamePattern})-(?:${specificNamePattern})))"
            } else {
                "((?:${specificNumberPattern})-(?:${specificNumberPattern}))"
            }
            return super.additionalParts + "|$betweenPattern"
        }
}