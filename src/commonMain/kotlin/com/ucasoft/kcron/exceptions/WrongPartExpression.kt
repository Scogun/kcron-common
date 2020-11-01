package com.ucasoft.kcron.exceptions

import com.ucasoft.kcron.parsers.BaseParser

class WrongPartExpression(part: String, internal val specificParser: BaseParser<*>) : Throwable("Part $part is not ${specificParser.partName} expression!")
