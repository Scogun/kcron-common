package com.ucasoft.kcron.extensions

class At(private val every: Int, private val starting: Int) {

    override fun toString() = "$starting/$every"
}

infix fun Int.at(starting: Int) : At = At(this, starting)