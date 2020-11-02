package com.ucasoft.kcron.extensions

class At(private val starting: Int, private val every: Int) {

    override fun toString() = "$starting/$every"
}

infix fun Int.at(every: Int) : At = At(this, every)