package com.ucasoft.kcron.abstractions

interface CronDateTime<out T> {

    val year: Int

    val month: Int

    val dayOfMonth: Int

    val isoDayOfWeek: Int

    val hour: Int

    val minute: Int

    val second: Int

    fun cast() : T

    fun plusDays(days: Int): CronDateTime<T>

    fun minusDays(days: Int): CronDateTime<T>
}