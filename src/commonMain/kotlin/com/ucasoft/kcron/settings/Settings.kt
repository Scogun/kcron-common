package com.ucasoft.kcron.settings

import com.ucasoft.kcron.common.WeekDays

data class Settings(var firstDayOfWeek: WeekDays = WeekDays.Monday, var version: Version = Version.Auto)