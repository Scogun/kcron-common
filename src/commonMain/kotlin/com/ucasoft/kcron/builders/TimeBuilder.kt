package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.TimeGroups

abstract class TimeBuilder : PartBuilder<TimeGroups>() {

    abstract val defaultEnd: Int

    protected lateinit var values : List<Int>

    override fun build(type: TimeGroups, value: String) {
        if (type == TimeGroups.Specific) {
            values = value.split(',').map { v -> v.toInt() }.sorted()
        } else {
            var start = 0
            var end = defaultEnd
            var step = 1
            if (type == TimeGroups.EveryStartingAt || type == TimeGroups.EveryBetween) {
                val data = value.split(if (type == TimeGroups.EveryStartingAt) '/' else '-').map { v -> v.toInt() }
                start = data[0]
                if (type == TimeGroups.EveryStartingAt) {
                    step = data[1]
                } else {
                    end = data[1]
                }
            }
            values = listOf(start..end step step).flatten()
        }
    }
}