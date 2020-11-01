package com.ucasoft.kcron.parsers

import com.ucasoft.kcron.common.TimeGroups
import kotlin.test.*

class SecondsPartTests {

    lateinit var parser: SecondsMinutesParser

    private val allowedValues = listOf(
        TimeValueGroup("*", TimeGroups.Any),
        TimeValueGroup("0", TimeGroups.Specific),
        TimeValueGroup("15", TimeGroups.Specific),
        TimeValueGroup("59", TimeGroups.Specific),
        TimeValueGroup("0,59", TimeGroups.Specific),
        TimeValueGroup("0,10,15,35,55", TimeGroups.Specific),
        TimeValueGroup("0-59", TimeGroups.EveryBetween),
        TimeValueGroup("5-15", TimeGroups.EveryBetween),
        TimeValueGroup("10-45", TimeGroups.EveryBetween),
        TimeValueGroup("0/59", TimeGroups.EveryStartingAt),
        TimeValueGroup("10/45", TimeGroups.EveryStartingAt),
        TimeValueGroup("59/59", TimeGroups.EveryStartingAt),
        TimeValueGroup("59/60",  TimeGroups.EveryStartingAt)
    )

    private val deniedValues = listOf(
        "**",
        "60",
        "15,",
        ",59",
        "0,60",
        "0,10,15,35,55,72",
        "0-",
        "-15",
        "10-60",
        "60/59",
        "59/61"
    )

    @BeforeTest
    fun setupOnce() {
        parser = SecondsMinutesParser("seconds and minutes")
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
            assertEquals(TimeGroups.Unknown, parser.group)
        }
    }
}