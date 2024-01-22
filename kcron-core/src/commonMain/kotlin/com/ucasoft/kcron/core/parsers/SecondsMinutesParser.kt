package com.ucasoft.kcron.core.parsers

import com.ucasoft.kcron.core.common.CronPart

class SecondsMinutesParser(part: CronPart) : TimeParser(part,"[0-9]|[1-5][0-9]", "[0-9]|[1-5][0-9]|60")