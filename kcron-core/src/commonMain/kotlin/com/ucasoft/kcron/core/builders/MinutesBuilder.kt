package com.ucasoft.kcron.core.builders

class MinutesBuilder : TimeBuilder() {

    val minutes : List<Int>
        get() {
            return values
        }
}