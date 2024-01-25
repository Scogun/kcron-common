package com.ucasoft.kcron.core.exceptions

class UnknownCronPart(part: String) : Throwable("Unknown Cron part $part!")