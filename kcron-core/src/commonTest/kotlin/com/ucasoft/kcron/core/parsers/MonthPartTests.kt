package com.ucasoft.kcron.core.parsers

import com.ucasoft.kcron.core.common.MonthGroups
import io.kotest.assertions.withClue
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlin.test.*

class MonthPartTests {

    private lateinit var parser: MonthsParser

    private val allowedValues = listOf(
        com.ucasoft.kcron.core.parsers.MonthValueGroup("*", MonthGroups.Any),
        com.ucasoft.kcron.core.parsers.MonthValueGroup("1", MonthGroups.Specific),
        com.ucasoft.kcron.core.parsers.MonthValueGroup("6", MonthGroups.Specific),
        com.ucasoft.kcron.core.parsers.MonthValueGroup("12", MonthGroups.Specific),
        com.ucasoft.kcron.core.parsers.MonthValueGroup("JAN", MonthGroups.Specific),
        com.ucasoft.kcron.core.parsers.MonthValueGroup("JUN", MonthGroups.Specific),
        com.ucasoft.kcron.core.parsers.MonthValueGroup("DEC", MonthGroups.Specific),
        com.ucasoft.kcron.core.parsers.MonthValueGroup("1,12", MonthGroups.Specific),
        com.ucasoft.kcron.core.parsers.MonthValueGroup("JAN,DEC", MonthGroups.Specific),
        com.ucasoft.kcron.core.parsers.MonthValueGroup("1-12", MonthGroups.EveryBetween),
        com.ucasoft.kcron.core.parsers.MonthValueGroup("6-8", MonthGroups.EveryBetween),
        com.ucasoft.kcron.core.parsers.MonthValueGroup("1/1", MonthGroups.EveryStartingAt),
        com.ucasoft.kcron.core.parsers.MonthValueGroup("9/6", MonthGroups.EveryStartingAt),
        com.ucasoft.kcron.core.parsers.MonthValueGroup("12/12", MonthGroups.EveryStartingAt)
    )

    private val deniedValues = listOf(
        "**",
        "0",
        "13",
        "6,",
        ",12",
        "1,13",
        "1,10,13",
        "JUC",
        "JUC,DEC",
        "JUN,DAY",
        "1-",
        "-12",
        "10-13",
        "0/12",
        "1/13",
        "13/12"
    )

    @BeforeTest
    fun setupOnce() {
        parser = MonthsParser()
    }

    @Test
    fun allowedValues() {
        for (value in allowedValues) {
            parser.parse(value.value)
            parser.parse(value.value)
            withClue(value.value) {
                parser.isValid.shouldBeTrue()
            }
            parser.group.shouldBe(value.group)
        }
    }

    @Test
    fun deniedValues() {
        for (value in deniedValues) {
            parser.parse(value)
            parser.isValid.shouldBeFalse()
            parser.group.shouldBe(MonthGroups.Unknown)
        }
    }
}