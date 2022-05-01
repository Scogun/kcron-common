package com.ucasoft.kcron.extensions

import com.ucasoft.kcron.common.WeekDays

class At(private val every: Int, private val starting: String) {
    constructor(every: Int, starting: WeekDays) : this(every, starting.shortName)

    override fun toString() = "$starting/$every"
}

infix fun Int.at(starting: Int) : At = At(this, starting.toString())

infix fun Int.at(starting: WeekDays) : At = At(this, starting)