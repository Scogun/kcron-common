package com.ucasoft.kcron.kotlinx.datetime

import com.ucasoft.kcron.abstractions.CronDateTimeProvider
import kotlinx.datetime.LocalDateTime

class CronLocalDateTimeProvider : CronDateTimeProvider<LocalDateTime, CronLocalDateTime> {

    override fun now() = CronLocalDateTime()

    override fun from(year: Int, month: Int, day: Int, hours: Int, minutes: Int, seconds: Int) =
        CronLocalDateTime(year, month, day, hours, minutes, seconds)

    override fun from(original: LocalDateTime) = original.toCronLocalDateTime()

}