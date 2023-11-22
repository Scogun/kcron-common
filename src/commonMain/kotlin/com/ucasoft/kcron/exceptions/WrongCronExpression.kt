package com.ucasoft.kcron.exceptions

import com.ucasoft.kcron.settings.Version

class WrongCronExpression(expression: String, version: Version) : Throwable("Expression $expression is not ${if (version != Version.Auto) "$version " else ""}Cron one!")
