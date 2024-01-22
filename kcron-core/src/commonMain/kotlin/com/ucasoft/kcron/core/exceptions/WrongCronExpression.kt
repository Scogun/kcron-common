package com.ucasoft.kcron.core.exceptions

import com.ucasoft.kcron.core.settings.Version

class WrongCronExpression(expression: String, version: Version) : Throwable("Expression $expression is not ${if (version != Version.Auto) "$version " else ""}Cron one!")
