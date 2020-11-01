package com.ucasoft.kcron.exceptions

import com.ucasoft.kcron.parsers.BaseParser

class WrongPartExpression(partValue: String, internal val specificParser: BaseParser<*>) : Throwable("Value $partValue is not the valid expression for the ${specificParser.part.partName} part!")
