package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.exceptions.WrongCronExpression
import com.ucasoft.kcron.exceptions.WrongPartCombinations
import com.ucasoft.kcron.exceptions.WrongPartExpression
import com.ucasoft.kcron.exceptions.WrongPartsExpression
import com.ucasoft.kcron.settings.Version
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowWithMessage
import kotlin.test.Test

class ParserTests {

    private val parser = Parser()

    @Test
    fun goodAutoExpressions() {
        parser.parse("* * * ? * * *")
        parser.parse("* * ? * *")
        parser.parse("* * * 29 * ? *")
        parser.parse("* * ? * ?")
    }

    @Test
    fun goodClassicExpressions() {
        parser.parse("* * ? * *", Version.Classic)
        parser.parse("* * 29 * ?", Version.Classic)
        parser.parse("* * ? * 1L", Version.Classic)
        parser.parse("* * ? * MON/1", Version.Classic)
    }

    @Test
    fun goodModernExpressions() {
        parser.parse("* * * ? * * *", Version.Modern)
        parser.parse("* * * 29 * ? *", Version.Modern)
        parser.parse("* * * ? * 1L *", Version.Modern)
        parser.parse("* * * ? * MON/1 *", Version.Modern)
    }

    @Test
    fun badAutoExpressions() {
        shouldThrowWithMessage<WrongCronExpression>("Expression * * * is not Cron one!") { parser.parse("* * *") }
        shouldThrow<WrongCronExpression> { parser.parse("* * * ? ? * * *") }
    }

    @Test
    fun badClassicExpressions() {
        shouldThrow<WrongCronExpression> { parser.parse("* * ? * * *", Version.Classic) }
        shouldThrowWithMessage<WrongCronExpression>("Expression * * * ? * * * is not Classic Cron one!") { parser.parse("* * * ? * * *", Version.Classic) }
    }

    @Test
    fun badModernExpressions() {
        shouldThrow<WrongCronExpression> { parser.parse("* * ? * *", Version.Modern) }
        shouldThrowWithMessage<WrongCronExpression>("Expression * * * ? ? * * * is not Modern Cron one!") { parser.parse("* * * ? ? * * *", Version.Modern) }
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