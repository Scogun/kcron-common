package com.ucasoft.kcron.exceptions

class WrongCronExpression(expression: String) : Throwable("Expression $expression is not Cron one!")
