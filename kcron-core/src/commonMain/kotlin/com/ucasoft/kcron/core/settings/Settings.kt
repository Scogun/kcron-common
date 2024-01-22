package com.ucasoft.kcron.core.settings

import com.ucasoft.kcron.core.common.WeekDays

data class Settings(var firstDayOfWeek: WeekDays = WeekDays.Monday, var version: Version = Version.Auto)