package com.ucasoft.kcron.core.exceptions

class WrongPartsExpression(innerExceptions: List<WrongPartExpression>) : Throwable("Parts ${innerExceptions.map { ie -> ie.specificParser.part }.joinToString()} contain wrong expressions!")
