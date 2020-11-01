package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.CronGroups

abstract class BaseParser<T> where T : Enum<T>, T: CronGroups {

    internal abstract val partName: String

    protected abstract val pattern: String

    private var regex: Regex? = null

    private var matches: MatchResult? = null

    abstract val unknownGroup : T

    abstract val groups: Array<T>

    val isValid : Boolean
        get() {
            if (matches != null) {
                return matches!!.groups.isNotEmpty()
            }

            return false
        }

    val group: T
        get() {
            if (isValid) {
                for (group in groups.filter { g -> g.index > 0 }) {
                    if (matches!!.groups[group.index] != null) {
                        return group
                    }
                }
            }

            return unknownGroup
        }

    fun parse(expression: String) {
        if (regex == null) {
            regex = pattern.toRegex()
        }
        matches = regex!!.matchEntire(expression)
    }
}