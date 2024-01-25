package com.ucasoft.kcron.core.builders

class HoursBuilder : TimeBuilder() {

    override val defaultEnd = 23

    val hours: List<Int>
        get() {
            return values
        }
}