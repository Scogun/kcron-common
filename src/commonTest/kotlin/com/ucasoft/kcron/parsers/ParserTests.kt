package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.exceptions.WrongCronExpression
import com.ucasoft.kcron.exceptions.WrongPartCombinations
import com.ucasoft.kcron.exceptions.WrongPartExpression
import com.ucasoft.kcron.exceptions.WrongPartsExpression
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowWithMessage
import kotlin.test.Test

class ParserTests {

    private val parser = Parser()

    @Test
    fun goodExpressions() {
        parser.parse("* * * ? * * *")
        parser.parse("* * * 29 * ? *")
        parser.parse("* * * ? * 1L *")
        parser.parse("* * * ? * MON/1 *")
    }

    @Test
    fun badExpressions() {
        shouldThrowWithMessage<WrongCronExpression>("Expression * * * is not Cron one!") { parser.parse("* * *") }
        shouldThrow<WrongCronExpression> { parser.parse("* * * ? ? * * *") }
    }

    @Test
    fun badExpressionParts() {
        shouldThrow<WrongPartExpression>{ parser.parse("62 * * ? * * *") }
        shouldThrow<WrongPartExpression> { parser.parse("* * * ? * MON#6 *") }
        shouldThrow<WrongPartsExpression> { parser.parse("62 * 25 ? * * *") }
    }

    @Test
    fun badCombinationParts() {
        shouldThrow<WrongPartCombinations> { parser.parse("* * * 1/31 * 1/1 *") }
        shouldThrow<WrongPartCombinations> { parser.parse("* * * 31/31 * SUN *") }
    }
}