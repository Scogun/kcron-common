package com.ucasoft.kcron.islandtime

import com.ucasoft.kcron.abstractions.CronDateTimeProvider
import io.islandtime.DateTime

class CronIslandDateTimeProvider: CronDateTimeProvider<DateTime, CronIslandDateTime> {

    override fun now() = CronIslandDateTime()

    override fun from(year: Int, month: Int, day: Int, hours: Int, minutes: Int, seconds: Int) =
        CronIslandDateTime(year, month, day, hours, minutes, seconds)

    override fun from(original: DateTime) = original.toCronIslandDateTime()
}