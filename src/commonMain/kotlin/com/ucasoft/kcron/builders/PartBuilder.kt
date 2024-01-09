package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.CronGroups

abstract class PartBuilder<T: CronGroups> {

    var value: String = "*"
        private set

    abstract fun build(type: T, value: String)

    @Suppress("UNCHECKED_CAST")
    fun commonBuild(type: CronGroups, value: String) {
        build(type as T, value)
        this.value = value
    }
}