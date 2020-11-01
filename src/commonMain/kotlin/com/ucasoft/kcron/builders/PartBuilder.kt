package com.ucasoft.kcron.builders

import com.ucasoft.kcron.common.CronGroups

abstract class PartBuilder<T: CronGroups> {

    lateinit var value: String
        private set

    abstract fun build(type: T, value: String)

    fun commonBuild(type: CronGroups, value: String) {
        build(type as T, value)
        this.value = value
    }
}