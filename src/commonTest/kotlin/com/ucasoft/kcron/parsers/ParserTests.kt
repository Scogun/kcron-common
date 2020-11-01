package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.exceptions.WrongCronExpression
import com.ucasoft.kcron.exceptions.WrongPartCombinations
import com.ucasoft.kcron.exceptions.WrongPartExpression
import com.ucasoft.kcron.exceptions.WrongPartsExpression
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ParserTests {

    private val parser = Parser()

    @Test
    fun goodExpressions() {
        parser.parse("* * * ? * * *")
        parser.parse("* * * 29 * ? *")
        parser.parse("* * * ? * 1L *")
    }

    @Test
    fun badExpressions() {
        assertFailsWith<WrongCronExpression> { parser.parse("* * *") }
        assertFailsWith<WrongCronExpression> { parser.parse("* * * ? ? * * *") }
    }

    @Test
    fun badExpressionParts() {
        assertFailsWith<WrongPartExpression>{ parser.parse("62 * * ? * * *") }
        assertFailsWith<WrongPartsExpression> { parser.parse("62 * 25 ? * * *") }
    }

    @Test
    fun badCombinationParts() {
        assertFailsWith<WrongPartCombinations> { parser.parse("* * * 1/31 * 1/1 *") }
        assertFailsWith<WrongPartCombinations> { parser.parse("* * * 31/31 * SUN *") }
    }
}