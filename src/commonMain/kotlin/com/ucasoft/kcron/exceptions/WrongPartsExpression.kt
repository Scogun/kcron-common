package com.ucasoft.kcron.exceptions

class WrongPartsExpression(val innerExceptions: List<WrongPartExpression>) : Throwable("Parts ${innerExceptions.map { ie -> ie.specificParser.partName }.joinToString()} contain wrong expressions!")
