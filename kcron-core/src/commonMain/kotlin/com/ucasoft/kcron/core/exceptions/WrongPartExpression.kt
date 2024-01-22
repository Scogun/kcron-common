package com.ucasoft.kcron.core.exceptions

import com.ucasoft.kcron.core.parsers.BaseParser

class WrongPartExpression(partValue: String, internal val specificParser: BaseParser<*>) : Throwable("Value $partValue is not the valid expression for the ${specificParser.part.partName} part!")
