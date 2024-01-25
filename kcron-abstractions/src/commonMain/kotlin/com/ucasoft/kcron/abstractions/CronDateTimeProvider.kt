package com.ucasoft.kcron.abstractions

interface CronDateTimeProvider<T, out D> where D: CronDateTime<T> {

    fun now(): D

    fun from(year: Int, month: Int, day: Int, hours: Int = 0, minutes: Int = 0, seconds: Int = 0): D

    fun from(original: T) : D
}