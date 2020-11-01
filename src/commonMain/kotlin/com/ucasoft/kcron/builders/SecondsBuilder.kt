package com.ucasoft.kcron.builders

class SecondsBuilder : TimeBuilder() {

    override val defaultEnd = 59

    val seconds : List<Int>
        get() {
            return values
        }
}