package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.CronPart

class HoursParser : TimeParser(CronPart.Hours,"[0-9]|1[0-9]|2[0-3]", "[0-9]|1[0-9]|2[0-4]")