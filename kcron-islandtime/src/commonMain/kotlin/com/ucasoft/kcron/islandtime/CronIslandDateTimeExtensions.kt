package com.ucasoft.kcron.islandtime

import io.islandtime.DateTime

fun DateTime.toCronIslandDateTime() =
    CronIslandDateTime(this.year, this.monthNumber, this.dayOfMonth, this.hour, this.minute, this.second)