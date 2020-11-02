package com.ucasoft.kcron.extensions

class On(private val dayOfWeek: Int, private val index: Int) {

    override fun toString() = "$dayOfWeek#$index"
}

infix fun Int.on(index: Int) : On = On(this, index)