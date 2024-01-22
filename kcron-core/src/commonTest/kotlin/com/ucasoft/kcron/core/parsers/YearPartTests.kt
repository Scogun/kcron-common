package com.ucasoft.kcron.core.parsers

import com.ucasoft.kcron.core.common.YearGroups
import io.kotest.assertions.withClue
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlin.test.*

class YearPartTests {

    private lateinit var parser: com.ucasoft.kcron.core.parsers.YearsParser

    private val allowedValues = listOf(
        YearValueGroup("*", YearGroups.Any),
        YearValueGroup("2020", YearGroups.Specific),
        YearValueGroup("2035", YearGroups.Specific),
        YearValueGroup("2099", YearGroups.Specific),
        YearValueGroup("2020,2099", YearGroups.Specific),
        YearValueGroup("2020,2030,2055,2099", YearGroups.Specific),
        YearValueGroup("2020-2099", YearGroups.EveryBetween),
        YearValueGroup("2025-2045", YearGroups.EveryBetween),
        YearValueGroup("2040-2075", YearGroups.EveryBetween),
        YearValueGroup("2020/100", YearGroups.EveryStartingAt),
        YearValueGroup("2030/45", YearGroups.EveryStartingAt),
        YearValueGroup("2099/99", YearGroups.EveryStartingAt)
    )

    private val deniedValues = listOf(
        "**",
        "2019",
        "2100",
        "2020,",
        ",2099",
        "2020,2100",
        "2020,2030,2035,2045,2055,2101",
        "2020-",
        "-2050",
        "2020-2100",
        "101/2050",
        "100/2100"
    )

    @BeforeTest
    fun setupOnce() {
        parser = com.ucasoft.kcron.core.parsers.YearsParser()
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
            parser.group.shouldBe(YearGroups.Unknown)
        }
    }
}