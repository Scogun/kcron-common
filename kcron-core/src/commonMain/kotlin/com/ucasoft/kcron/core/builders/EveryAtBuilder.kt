package com.ucasoft.kcron.core.builders

import com.ucasoft.kcron.core.common.CronGroups

abstract class EveryAtBuilder<T: CronGroups> : PartBuilder<T>() {

    protected lateinit var values : List<Int>

    abstract val defaultStart: Int

    abstract val defaultEnd: Int

    abstract val specific : T

    abstract val everyBetween : T

    abstract val everyStartingAt : T

    override fun build(type: T, value: String) {
        values = if (type == specific) {
            calculateSpecific(value)
        } else {
            calculateRange(type, value)
        }
    }

    protected open fun calculateSpecific(value: String): List<Int> = value.split(',').map { v -> v.toInt() }.sorted()

    protected fun calculateRange(type: T, value: String): List<Int> {
        var start = defaultStart
        var end = defaultEnd
        var step = 1
        if (type == everyStartingAt || type == everyBetween) {
            val data = value.split(if (type == everyStartingAt) '/' else '-').map { v -> v.toInt() }
            start = data[0]
            if (type == everyStartingAt) {
                step = data[1]
            } else {
                end = data[1]
            }
        }
        return listOf(start..end step step).flatten()
    }
}