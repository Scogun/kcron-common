package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.YearGroups
import kotlin.test.*

class YearPartTests {

    private lateinit var parser: YearsParser

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
        parser = YearsParser()
    }

    @Test
    fun allowedValues() {
        for (value in allowedValues) {
            parser.parse(value.value)
            assertTrue(parser.isValid, value.value)
            assertEquals(value.group, parser.group)
        }
    }

    @Test
    fun deniedValues() {
        for (value in deniedValues) {
            parser.parse(value)
            assertFalse(parser.isValid)
            assertEquals(YearGroups.Unknown, parser.group)
        }
    }
}