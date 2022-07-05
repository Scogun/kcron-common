package com.ucasoft.kcron.exceptions

class UnknownCronPart(part: String) : Throwable("Unknown Cron part $part!")