package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.CronGroups

abstract class AnySpecificParser<T>(
    private val anyPattern: String,
    protected val specificNumberPattern: String,
    private val specificNamePattern: String = "") : BaseParser<T>() where T: Enum<T>, T: CronGroups {

    abstract val additionalParts: String

    override val pattern: String
        get() {
            val specificPattern = if (specificNamePattern.isNotEmpty()) {
                "((?:(?:$specificNumberPattern)(?:,(?:$specificNumberPattern))*)|(?:(?:$specificNamePattern)(?:,(?:$specificNamePattern))*))"
            } else {
                "((?:$specificNumberPattern)(?:,(?:$specificNumberPattern))*)"
            }
            return "^(?:($anyPattern)|$specificPattern$additionalParts)$"
        }
}