package com.ucasoft.kcron.builders

class MinutesBuilder : TimeBuilder() {

    override var defaultEnd = 59

    val minutes : List<Int>
        get() {
            return values
        }
}